package com.yaoyyy.rubbish.user.exception;

import com.yaoyyy.rubbish.common.entity.user.User;

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
public class UserNotFound extends RuntimeException {

    public static final String msg = "用户不存在";

    private User user;

    public User getUid() {
        return user;
    }

    public UserNotFound(User user) {
        super(msg);
        this.user = user;
    }

    public UserNotFound(String message, Throwable cause, User user) {
        super(msg, cause);
        this.user = user;
    }

    public UserNotFound(Throwable cause, User user) {
        super(cause);
        this.user = user;
    }

    public UserNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, User user) {
        super(msg, cause, enableSuppression, writableStackTrace);
        this.user = user;
    }
}
