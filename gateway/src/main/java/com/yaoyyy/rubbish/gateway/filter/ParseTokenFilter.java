package com.yaoyyy.rubbish.gateway.filter;

import com.yaoyyy.rubbish.common.CodeEnum;
import com.yaoyyy.rubbish.common.R;
import com.yaoyyy.rubbish.gateway.utils.WebFluxUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;



/**
 * 　　　　　　　 ┏┓　 ┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　┃
 * 　　　　　　　┃　　　━　　 ┃ ++ + + +
 * 　　　　　　 ████━████  ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　 ┃　　　┃
 * 　　　　　　　　 ┃　　　┃ + + + +
 * 　　　　　　　　 ┃　　　┃　　　　Code is far away from bug with the animal protecting
 * 　　　　　　　　 ┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　 ┃　　　┃
 * 　　　　　　　　 ┃　　　┃　　+
 * 　　　　　　　　 ┃　 　 ┗━━━┓ + +
 * 　　　　　　　　 ┃ 　　　　   ┣┓
 * 　　　　　　　　 ┃ 　　　　　 ┏┛
 * 　　　　　　　　 ┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　  ┃┫┫ ┃┫┫
 * 　　　　　　　　  ┗┻┛ ┗┻┛+ + + +
 * <p>
 * rubbish-parent
 * 2019-03-06 22:19
 *
 * token解析过滤器
 * 此过滤器是从请求cookie中拿到token，并且将token解析成用户信息分发到下级路由
 *
 * @author yaoyang
 */
@AllArgsConstructor
@Slf4j
@Component
public class ParseTokenFilter implements GlobalFilter, Ordered {

    RestTemplate restTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();

        // 判断用户是否是登陆请求
        if (StringUtils.pathEquals("/oauth/login", request.getPath().toString())
                || StringUtils.pathEquals("/oauth/token", request.getPath().toString())) {

            return chain.filter(exchange);
        }

        // 从cookie获取token
        List<HttpCookie> rubbishes = request.getCookies().get("RUBBISH");
        if (rubbishes == null) {
            return chain.filter(exchange);
        }
        HttpCookie rubbish = rubbishes.get(0);
        String[] split = rubbish.getValue().split("RUBBISH=");
        String token = split[0];
        Claims claims;
        // 解析token
        try {
            claims = Jwts.parser().setSigningKey("Y".getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
        } catch (SignatureException e) {
            log.warn("jwt被修改，签名不正确！用户名");
            return chain.filter(exchange);
        } catch (ExpiredJwtException e) {
            // token过期
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return WebFluxUtils.createVoidMono(exchange, R.error(CodeEnum.REQUIRE_LOGIN, "登录失效"));
        }

        // 获取jwt中包含的用户信息
        String user_name = (String) claims.get("user_name");
        Long uid = Long.valueOf((String) claims.get("id"));
        List<?> authorities = claims.get("authorities", List.class);

        // 判断过期时间
        Date exp = claims.get("exp", Date.class);
        if (exp.getTime() - System.currentTimeMillis() < 1000 * 60 * 60) {
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.add("Authorization", "Basic Z2F0ZXdheTphZG1pbg==");
            // 认证参数
            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("refresh_token", split[1]);
            requestBody.add("grant_type", "refresh_token");
// TODO: 2019/3/14 参数值提取到配置文件
            HttpEntity<MultiValueMap> entity = new HttpEntity<>(requestBody, requestHeaders);
            ResponseEntity<String> responseEntity = restTemplate.exchange("http://AUTH-SERVER/oauth/token?id=" + uid, HttpMethod.POST, entity, String.class);
            List<String> list = responseEntity.getHeaders().get("Set-Cookie");
            exchange.getResponse().getHeaders().put("Set-Cookie", list);
        }

        URI uri = request.getURI();
        StringBuilder query = new StringBuilder();
        String originalQuery = uri.getRawQuery();

        if (StringUtils.hasText(originalQuery)) {
            query.append(originalQuery);
            if (originalQuery.charAt(originalQuery.length() - 1) != '&') {
                query.append('&');
            }
        }

        // TODO: 2019/3/12 优化时可把参数拼接代码段封装成方法，否则后期再添加参数又得再后面append
        // 参数拼接
        query.append("username").append("=").append(user_name).append("&").append("roles").append("=");
        if (authorities != null) {
            authorities.forEach(role -> query.append(role).append(","));
        }
        // 删掉最后一个逗号
        query.deleteCharAt(query.length() - 1);
        query.append("&").append("id=").append(uid);

        try {
            URI newUri = UriComponentsBuilder.fromUri(uri)
                    .replaceQuery(query.toString())
                    .build(true)
                    .toUri();

            ServerHttpRequest warpRequest = request.mutate().uri(newUri).build();

            return chain.filter(exchange.mutate().request(warpRequest).build());
        } catch (RuntimeException ex) {
            throw new IllegalStateException("Invalid URI query: \"" + query.toString() + "\"");
        }
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
