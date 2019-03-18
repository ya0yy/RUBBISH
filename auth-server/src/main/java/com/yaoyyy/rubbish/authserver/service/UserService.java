package com.yaoyyy.rubbish.authserver.service;

import com.yaoyyy.rubbish.common.R;
import com.yaoyyy.rubbish.authserver.config.JwtTokenEnhancer;
import com.yaoyyy.rubbish.authserver.feign.UserClient;
import com.yaoyyy.rubbish.authserver.pojo.UserAuthTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
 *
 * @author YaoYY
 * 2019-01-22 21:48
 */
@Component
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtTokenEnhancer jwtTokenEnhancer;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 获取用户认证信息
        R<UserAuthTO> r = userClient.userAuth(username);
        UserAuthTO userAuth = r.getData();
        /**
         * 线程变量保存uid，封装token时取出{@link JwtTokenEnhancer#enhance}
         */
        jwtTokenEnhancer.getUid().set(userAuth.getUid());

        return new User(userAuth.getUsername(),
                passwordEncoder.encode(userAuth.getPassword()),
                AuthorityUtils.createAuthorityList(userAuth.getRoles().toArray(new String[0])));
    }

}
