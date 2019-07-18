package com.yaoyyy.rubbish.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaoyyy.rubbish.common.model.user.User;
import com.yaoyyy.rubbish.common.model.user.UserAuthTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component("该注解是为了解决idea报红问题")
public interface UserMapper extends BaseMapper<User> {

    /**
     * test
     */
    int queryUserCount(@Param("uid") Long uid);

    /**
     * 通过uid查询用户密码
     *
     * @param uid 用户唯一id
     * @return java.lang.String
     * @author YaoYY
     * @date 2019-02-28 PM 07:47:41
     */
    String queryUserPass(@Param("uid") Long uid);

    /**
     * 2019/03/17
     * 通过用户名查询需要认证的用户信息
     * @param username 用户名
     * @return com.yaoyyy.rubbish.user.pojo.UserAuthTO
     * @author YaoYY
     */
    UserAuthTO queryUserAuth(@Param("username") String username);
}
