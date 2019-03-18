package com.yaoyyy.rubbish.user.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 用户基础信息
 */
@Data
@Accessors(chain = true)
@TableName("rb_user")
public class User {

    @TableId(type = IdType.AUTO)
    private Long uid;
    private String username;
    private String email;
    private Boolean emailActiveStatus;
    private String phoneNumber;
    private Boolean phoneNumberActiveStatus;
    private String profilePhoto;
    private String account;
    private Boolean accountStatus;
    private String password;
    private Boolean userStatus;
    private Date registDate;
    private Date lastOnlineDate;
    private Long userType;

}
