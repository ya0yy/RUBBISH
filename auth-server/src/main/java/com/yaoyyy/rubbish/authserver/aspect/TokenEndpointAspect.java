package com.yaoyyy.rubbish.authserver.aspect;

import com.yaoyyy.rubbish.authserver.config.AuthServerProperties;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
 *
 * 由于spring oauth2框架为我们生成token后，直接是以json形式返回给调用方，这里做一个aop增强，将token写入到浏览器cookie中
 * 2019-03-05 00:13
 *
 * @author yaoyang
 */
@Aspect
@Component
public class TokenEndpointAspect {

    public static final String dataPoint =
            "execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.postAccessToken(..))";

    /**
     * 配置属性类
     */
    @Autowired
    AuthServerProperties authServerProperties;

    @Pointcut(dataPoint)
    public void pointcut() {}

    @Around(dataPoint)
    public Object replaceResult(ProceedingJoinPoint point) throws Throwable {
        ResponseEntity<OAuth2AccessToken> response = (ResponseEntity<OAuth2AccessToken>) point.proceed();
        // 拿到token
        OAuth2AccessToken token = response.getBody();
        if (token == null || StringUtils.isBlank(token.getValue())) {
            return response;
        }
        return getResponse(token);
    }

    private ResponseEntity<OAuth2AccessToken> getResponse(OAuth2AccessToken accessToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        headers.set("Content-Type", "application/json;charset=UTF-8");

        // 过期时间（秒）转换成GMT标准格式
        String expireTime = ZonedDateTime.now(ZoneId.of("GMT"))
                .plusSeconds(authServerProperties.getTokenCookieExpireSeconds())
                .format(DateTimeFormatter.RFC_1123_DATE_TIME);

        // 拼接cookie内容
        StringBuilder cookieContent = new StringBuilder();
        cookieContent.append(authServerProperties.getTokenCookieName())
                .append("=").append(accessToken.getValue())
                .append("RUBBISH=").append(accessToken.getRefreshToken())
                .append(";expires=").append(expireTime)
                .append(";path=").append(authServerProperties.getTokenCookiePath());

        // 设置cookie过期时间
        headers.set("Set-Cookie",cookieContent.toString());
        // 清空响应体里的信息
        accessToken.getAdditionalInformation().clear();
        return new ResponseEntity<OAuth2AccessToken>(accessToken, headers, HttpStatus.OK);
    }
}
