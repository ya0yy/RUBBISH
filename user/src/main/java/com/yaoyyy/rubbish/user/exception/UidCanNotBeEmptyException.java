package com.yaoyyy.rubbish.user.exception;

import com.yaoyyy.rubbish.common.model.user.User;

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
 * 2019-02-28 11:05
 *
 * @author yaoyang
 */
public class UidCanNotBeEmptyException extends RuntimeException {

    private static final String MSG = "uid不能为null或者0";

    private User user;

    public User getUser() {
        return user;
    }

    public UidCanNotBeEmptyException(User user) {
        super(MSG);
        this.user = user;
    }

    public UidCanNotBeEmptyException(Throwable cause, User user) {
        super(MSG, cause);
        this.user = user;
    }

    public UidCanNotBeEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, User user) {
        super(MSG, cause, enableSuppression, writableStackTrace);
        this.user = user;
    }

    public UidCanNotBeEmptyException() {
        super(MSG);
    }
}
