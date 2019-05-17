package com.yaoyyy.rubbish.authserver.security.handler;

import com.yaoyyy.rubbish.authserver.config.AuthServerProperties;
import com.yaoyyy.rubbish.authserver.endpoint.AuthorizationCodeEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.support.SimpleSessionStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

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
@Slf4j
@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Lazy
    @Autowired
    AuthorizationCodeEndpoint authorizationCodeEndpoint;

    @Autowired
    AuthServerProperties authServerProperties;

    @Lazy
    @Autowired
    TokenEndpoint tokenEndpoint;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // 调用自定义的获取授权码的方法
        String code = getAuthorizeCode(authentication, request);
        log.info("用户 " + authentication.getName() + " 登陆成功，" + "授权码 " + code);
        Principal userPrincipal = request.getUserPrincipal();
        String path = "/oauth/token?client_id=%s&scope=%s&grant_type=authorization_code&client_secret=%s&code=%s";
        path = String.format(path,
                authServerProperties.getClient(),
                authServerProperties.getScope(),
                authServerProperties.getSecret(),
                code);

        response.setHeader("Content-Type", "text");
        response.getWriter().write(path);
    }

    private String getAuthorizeCode(Authentication authentication, HttpServletRequest request) {

        SessionStatus sessionStatus = new SimpleSessionStatus();
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

        return authorizationCodeEndpoint.authorize(new BindingAwareModelMap(), parameters, sessionStatus, principal);
    }

}
