package com.yaoyyy.rubbish.authserver.feign.fallback;

import com.yaoyyy.rubbish.common.R;
import com.yaoyyy.rubbish.authserver.feign.UserClient;
import com.yaoyyy.rubbish.common.model.user.Customer;
import com.yaoyyy.rubbish.common.model.user.UserAuthTO;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
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
 * <p>
 * rubbish-parent
 * 2019-03-13 22:57
 *
 * @author yaoyy
 */
@Slf4j
@Component
public class UserClientFallbackFactory implements FallbackFactory<UserClient> {


    @Override
    public UserClient create(Throwable cause) {
        return new UserClient() {

            @Override
            public R<String> userPwd(Long uid) {
                return R.ok("");
            }

            @Override
            public R<UserAuthTO> userAuth(String username) {
                log.warn(cause.getMessage());
                return R.error();
            }

            @Override
            public R<Customer> userInfo(Long uid) {
                return null;
            }
        };
    }
}
