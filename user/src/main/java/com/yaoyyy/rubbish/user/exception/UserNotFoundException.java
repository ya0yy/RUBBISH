package com.yaoyyy.rubbish.user.exception;

import com.yaoyyy.rubbish.common.model.user.Customer;

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
 * 2019-02-28 11:16
 *
 * @author yaoyang
 */
public class UserNotFoundException extends RuntimeException {

    private static final String MSG = "用户不存在";

    private Customer user;

    public Customer getUid() {
        return user;
    }

    public UserNotFoundException(Customer user) {
        super(MSG);
        this.user = user;
    }

    public UserNotFoundException(String message, Throwable cause, Customer user) {
        super(MSG, cause);
        this.user = user;
    }

    public UserNotFoundException(Throwable cause, Customer user) {
        super(cause);
        this.user = user;
    }

    public UserNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Customer user) {
        super(MSG, cause, enableSuppression, writableStackTrace);
        this.user = user;
    }
}
