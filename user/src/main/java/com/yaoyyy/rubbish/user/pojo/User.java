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

    @TableId(type = IdType.ID_WORKER)
    private Long uid;
    private String username;
    private String email;
    private String emailActiveStatus;
    private String phoneNumber;
    private String phoneNumberActiveStatus;
    private String profilePhoto;
    private String account;
    private String accountStatus;
    private String password;
    private String userStatus;
    private Date registDate;
    private Date lastOnlineDate;
    private Long userType;

}
