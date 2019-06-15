package com.yaoyyy.rubbish.authserver.oauth;

import com.yaoyyy.rubbish.authserver.config.AuthServerProperties;
import com.yaoyyy.rubbish.authserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

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
 * 2019-03-03 00:42
 *
 * @author yaoyang
 */
@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthServerProperties authServerProperties;

    @Autowired
    private JwtTokenEnhancer jwtTokenEnhancer;

    @Autowired
    private UserService userDetailsService;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory().withClient(authServerProperties.getClient())
                .secret(new BCryptPasswordEncoder().encode(authServerProperties.getSecret()))
                .authorizedGrantTypes("authorization_code", "password", "refresh_token")
                .scopes(authServerProperties.getScope())
                .accessTokenValiditySeconds(authServerProperties.getAccessTokenValiditySeconds())
                .refreshTokenValiditySeconds(authServerProperties.getRefreshTokenValiditySeconds())
                .redirectUris("http://example.com")
        ;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // new一个token增强器链，由于默认的已在DefaultTokenServices里写死，这里用到两个增强器。
        TokenEnhancerChain chain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancers = new ArrayList<>();
        // 1.在token中加入自定义属性
        enhancers.add(jwtTokenEnhancer);
        // 2.默认token转换成jwt
        enhancers.add(jwtAccessTokenConverter());
        chain.setTokenEnhancers(enhancers);

        endpoints
                // token增强
                .tokenEnhancer(chain)
                // 加入jwt
                .tokenStore(jwtTokenStore())
//                .accessTokenConverter(jwtAccessTokenConverter())
                // password认证模式需要给端点加入authenticationManager，这里使用security生成的。
                .authenticationManager(authenticationManager)
                // 如果需要refresh_token认证则需要配置UserDetailService
                .userDetailsService(userDetailsService)
                // 加入自定义的异常处理器
                .exceptionTranslator(new OAuth2ExceptionTranslator())

        ;
    }

    /**
     * jwt管理存储，其实并没有存储(详情baidu)
     */
    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * jwt转换器
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        // jwt签名
        jwtAccessTokenConverter.setSigningKey(authServerProperties.getJwtSigningKey());
        return jwtAccessTokenConverter;
    }

}
