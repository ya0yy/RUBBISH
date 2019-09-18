package com.yaoyyy.rubbish.user.service;

import com.yaoyyy.rubbish.common.model.user.Customer;
import com.yaoyyy.rubbish.common.model.user.UserAuthTO;

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
 * 2019-02-28 10:51
 * </p>
 *
 * @author yaoyy
 */
public interface UserService {

    /**
     * 查询用户信息
     *
     * @param uid 用户唯一id
     * @return com.yaoyyy.rubbish.user.pojo.User 用户实体
     * @author YaoYY
     * @date 2019-02-28 AM 11:22:39
     */
    Customer getUserInfo(Long uid);

    /**
     * 查询用户信息，带用户名校验
     * 2019/03/16
     * @param uid 用户id
     * @param username 用户名
     * @return com.yaoyyy.rubbish.user.pojo.User
     * @author YaoYY
     */
    Customer getUserInfo(Long uid, String username);

    /**
     * 查询密码
     *
     * @param uid {@link com.yaoyyy.rubbish.user.service.UserService#getUserInfo(java.lang.Long)}
     * @return java.lang.String
     * @author YaoYY
     * @date 2019-02-28 PM 07:44:22
     */
    String getUserPass(Long uid);

    /**
     * 2019/03/17
     * 通过用户名查询用户认证需要的信息
     * @param username 用户名
     * @return UserAuthTo
     * @author YaoYY
     */
    UserAuthTO getUserAuth(String username);
}
