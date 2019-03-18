package com.yaoyyy.rubbish.user.service.impl;

import com.yaoyyy.rubbish.user.exception.UidCanNotBeEmpty;
import com.yaoyyy.rubbish.user.exception.UserNotFound;
import com.yaoyyy.rubbish.user.mapper.UserMapper;
import com.yaoyyy.rubbish.user.pojo.User;
import com.yaoyyy.rubbish.user.pojo.UserAuthTO;
import com.yaoyyy.rubbish.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 * 2019-02-28 10:53
 *
 * @author yaoyang
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User getUserInfo(Long uid) {

        if (uidIsOk(uid)) throw new UidCanNotBeEmpty();

        // 查询
        User user = userMapper.selectById(uid);

        // 该用户是否存在
        if (user == null) {
            throw new UserNotFound(new User().setUid(uid));
        }

        return user;
    }

    @Override
    public User getUserInfo(Long uid, String username) {
        // TODO: 2019/3/16 重载逻辑没写完
        return null;
    }

    @Override
    public String getUserPass(Long uid) {
        if (uidIsOk(uid)) return "";
        return userMapper.queryUserPass(uid);
    }

    @Override
    public UserAuthTO getUserAuth(String username) {
        return StringUtils.isNotBlank(username) ? userMapper.queryUserAuth(username) : new UserAuthTO();
    }

    /**
     * uid是否合法
     */
    private boolean uidIsOk(Long uid) {
        return uid != null && uid > 0L;
    }
}
