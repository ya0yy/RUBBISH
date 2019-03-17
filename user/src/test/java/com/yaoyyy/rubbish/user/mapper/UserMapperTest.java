package com.yaoyyy.rubbish.user.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.yaoyyy.rubbish.user.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void testGetUserPass() {
        assertEquals(userMapper.getUserPass(1L), "123456");
    }

    @Test
    public void testCustom() {
        assertEquals(userMapper.getUserCount(1100442339115012097L), 1);
    }

    @Test
    public void testSelect() {

        User user = new User();
        Map<String, Object> map = Maps.newHashMap();
        map.put("name", "tom");
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
//        wrapper.allEq(User::getAccount, map);
        List<User> users = userMapper.selectList(wrapper);
    }

    @Test
    public void testInsert() {
        User user = new User();
        user.setUsername("jerry")
                .setEmail("jerry@qq.com")
                .setUserType(1L)
                .setPhoneNumber("120");
        int insert = userMapper.insert(user);
        ObjectMapper mapper = new ObjectMapper();
    }


    @Test
    public void testLambda() {
        Map<String, Object> conditionMap = new HashMap<>();
//使用表字段名
        conditionMap.put("name", "tom");
//allEq(BiPredicate<R, V> filter, Map<R, V> params, boolean null2IsNull)
//filter：忽略字段
//null2IsNull：为true则在map的value为null时调用 isNull 方法,为false时则忽略value为null的
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().allEq((r, v) -> r.indexOf("tom") > 0, conditionMap);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::print);
    }

    @Test
    public void test() {
        Wrapper<User> wrapper = new QueryWrapper<User>().lambda().eq(User::getAccount, 123);
        log.error(wrapper.getSqlSegment());

    }

}
