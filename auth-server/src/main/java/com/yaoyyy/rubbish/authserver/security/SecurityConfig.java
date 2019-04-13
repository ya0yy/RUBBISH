package com.yaoyyy.rubbish.authserver.security;

import com.yaoyyy.rubbish.authserver.config.AuthServerProperties;
import com.yaoyyy.rubbish.authserver.security.handler.AuthenticationFailureHandler;
import com.yaoyyy.rubbish.authserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

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
 * 2019-03-03 01:17
 *
 * @author yaoyang
 */
// TODO: 2019/3/23 如果不要security，oauth是否能独立运作
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthServerProperties authServerProperties;

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;

    /**
     * spring security规则
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 从配置文件中拿到放行路径并添加默认路径
        List<String> permits = authServerProperties.getPermits();
        permits.add("/");
        permits.add("/oauth**");
        permits.add("/error**");
        permits.add("/test**");

        http.formLogin()//.loginPage(authServerProperties.getLoginPage())
                // TODO: 2019/3/27 处理器目前没用，因为现在是直接通过oauth2 password 认证的方式登录的
                // 登录失败处理器
                .failureHandler(authenticationFailureHandler)
                // 登录成功处理器
                .successHandler(authenticationSuccessHandler)
                .and()
                // 关闭session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .antMatcher("/**").authorizeRequests()
                .antMatchers(permits.toArray(new String[0])).permitAll()
                .anyRequest().authenticated()
        ;
    }

    /**
     * 设置用户信息服务（spring security给我们比对用户密码时需要用到，设置密码加密器）
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    /**
     * 拿到WebSecurityConfigurer生成的AuthenticationManager，申明为Bean
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();

    }

    /**
     * 密码加密器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}