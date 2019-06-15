package com.yaoyyy.rubbish.authserver.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
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
 * 2019-03-10 15:45
 *
 * @author yaoyang
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "auth-server")
public class AuthServerProperties {

    /**
     * 保存token的cookie名称
     */
    private String tokenCookieName = "RUBBISH";
    /**
     * cookie过期时间（秒）
     */
    private Integer tokenCookieExpireSeconds = 60 * 60 * 10;
    /**
     * cookie路径
     */
    private String tokenCookiePath = "/";
    /**
     * cookie domain
     */
    private String tokenCookieDomain = "http://localhost:10001";
    /**
     * jwt签名
     */
    private String jwtSigningKey = "Y";
    /**
     * 客户端id
     */
    private String client = "gateway";
    /**
     * 客户端secret
     */
    private String secret = "admin";
    /**
     * 客户端权限
     */
    private String scope = "all";
    /**
     * token过期时间（秒）默认12小时
     */
    private Integer accessTokenValiditySeconds = 60 * 60 * 8; // default 12 hours.
    /**
     * 刷新码过期时间
     */
    private Integer refreshTokenValiditySeconds = 60 * 60 * 24 * 30; // default 30 days.
    /**
     * 未登录时跳转路径
     */
    private String loginPage = "/";
    /**
     * security放行路径（只针对本服务）
     */
    private List<String> permits = new ArrayList<>();

}
