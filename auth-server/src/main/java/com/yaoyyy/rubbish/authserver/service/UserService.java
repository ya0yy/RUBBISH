package com.yaoyyy.rubbish.authserver.service;

import com.yaoyyy.rubbish.authserver.feign.UserClient;
import com.yaoyyy.rubbish.common.model.user.UserAuthTO;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Component
public class UserService implements UserDetailsService {

    private PasswordEncoder passwordEncoder;

    private UserClient userClient;

    private ThreadLocal<Long> uid = new ThreadLocal<>();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 获取用户认证信息
        UserAuthTO userAuth = userClient.userAuth(username).getData();
        if (userAuth == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        this.uid.set(userAuth.getUid());

        return new User(userAuth.getUsername(),
                passwordEncoder.encode(userAuth.getPassword()),
                AuthorityUtils.createAuthorityList(userAuth.getRoles().toArray(new String[0])));
    }

    public Long getUid() {
        return uid.get();
    }
}
