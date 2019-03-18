package com.yaoyyy.rubbish.user.controller;

import com.yaoyyy.rubbish.common.R;
import com.yaoyyy.rubbish.user.pojo.User;
import com.yaoyyy.rubbish.user.pojo.UserAuthTO;
import com.yaoyyy.rubbish.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YaoYY
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取当前登录用户的信息")
    @GetMapping("/user_info")
    public R<User> userInfo(@RequestParam(value = "uid") Long uid,
                            @RequestParam(value = "username") String username) {
        User user = userService.getUserInfo(uid, username);
        return R.ok(user);
    }

    @ApiOperation(value = "通过uid查询用户信息")
    @GetMapping("/user_info/{uid}")
    public R<User> userInfo(@PathVariable("uid") Long uid) {
        User user = userService.getUserInfo(uid);
        return R.ok(user);
    }

    @ApiOperation(value = "通过用户名查询用户用认证信息")
    @GetMapping("/user_auth/{username}")
    public R<UserAuthTO> userAuth(@PathVariable("username") String username) {
        UserAuthTO auth= userService.getUserAuth(username);
        return R.ok(auth);
    }

    @ApiOperation(value = "通过uid查询用户密码")
    @GetMapping("/user_pwd/{uid}")
    public R<String> userPwd(@PathVariable("uid") Long uid) {
        String password = userService.getUserPass(uid);
        return R.ok(password);
    }

    @ApiOperation(value = "用于测试", notes = "测试")
    @PostMapping("/user_get_test/{uid}")
    public R<Map<String, Object>> userGetTest(@PathVariable("uid") Long uid,
                                              @RequestParam(value = "roles", required = false) List<String> roles,
                                              @RequestParam(value = "username", required = false) String uname,
                                              @RequestParam(value = "param", required = false) String param,
                                              @RequestBody(required = false) Map<String, String> body,
                                              HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("roles", roles);
        map.put("username", uname);
        map.put("param", param);
        map.put("body", body);
        map.put("method", request.getMethod());
        return R.ok(map);
    }

}
