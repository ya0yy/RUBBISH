package com.yaoyyy.rubbish.authserver.security.handler;

import com.yaoyyy.rubbish.authserver.config.AuthServerProperties;
import com.yaoyyy.rubbish.authserver.endpoint.AuthorizationCodeEndpoint;
import com.yaoyyy.rubbish.authserver.service.UserDetailServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.support.SimpleSessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;

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
 * 2019-03-26 17:11
 * spring security登录成功处理器， 这里帮用户拿到授权码并返回
 *
 * @author yaoyy
 */
@AllArgsConstructor
@Slf4j
@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    AuthServerProperties authServerProperties;

    // 认证端点
    @Lazy
    AuthorizationCodeEndpoint authorizationCodeEndpoint;

    UserDetailServiceImpl userService;

    private String getAuthorizeCodePathTemplate
            = "/oauth/token?client_id=%s&scope=%s&grant_type=authorization_code&client_secret=%s&code=%s&uid=%s";


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        // 获取uid
        Long uid = userService.getUid();

        // 调用自定义的获取授权码的方法
        String code = getAuthorizeCode(authentication);
        log.info("用户 " + authentication.getName() + " 登陆成功，" + "授权码 " + code);

        // 拼接认证地址
        String getAuthorizeCodePath = String.format(getAuthorizeCodePathTemplate,
                authServerProperties.getClient(),
                authServerProperties.getScope(),
                authServerProperties.getSecret(),
                code,
                uid);

        // 写回
        response.setHeader("Content-Type", "text");
        response.getWriter().write(getAuthorizeCodePath);
    }

    private String getAuthorizeCode(Authentication authentication) {

        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        // 从authentication中拿到PreAuthenticatedAuthenticationToke构造器所需要的参数
        PreAuthenticatedAuthenticationToken principal =
                new PreAuthenticatedAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials());
        // 设置已经登陆
        principal.setAuthenticated(true);
        // oauth2参数
        parameters.put("response_type", "code");
        parameters.put("client_id", authServerProperties.getClient());
        parameters.put("scope", authServerProperties.getScope());

        return authorizationCodeEndpoint.authorize(new BindingAwareModelMap(), parameters, new SimpleSessionStatus(), principal);
    }

}
