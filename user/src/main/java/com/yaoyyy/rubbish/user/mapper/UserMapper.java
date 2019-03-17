package com.yaoyyy.rubbish.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaoyyy.rubbish.user.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component("该注解是为了解决idea报红问题")
public interface UserMapper extends BaseMapper<User> {

    /**
     * test
     */
    int getUserCount(@Param("uid") Long uid);

    /**
     * 通过uid查询用户密码
     *
     * @param uid 用户唯一id
     * @return java.lang.String
     * @author YaoYY
     * @date 2019-02-28 PM 07:47:41
     */
    String getUserPass(@Param("uid") Long uid);

}
