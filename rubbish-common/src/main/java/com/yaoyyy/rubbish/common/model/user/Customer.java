package com.yaoyyy.rubbish.common.model.user;

import com.yaoyyy.rubbish.common.model.pojo.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * 用户基础信息
 * @author yaoyang
 */
@Accessors(chain = true)
@Getter
@Setter
@javax.persistence.Table(name = "rb_customer")
@Table(appliesTo = "rb_customer", comment = "用户主体表")
@Entity
@DynamicInsert
@DynamicUpdate
public class Customer extends BaseEntity {

    @Id
    @Column(columnDefinition = "VARCHAR(32) COMMENT '用户名'")
    private String username;

    @Column(columnDefinition = "VARCHAR(128) COMMENT '邮箱'")
    private String email;

    @Column(length = 32, columnDefinition = "TINYINT(1) COMMENT '邮箱是否激活'")
    private Boolean emailActiveStatus;

    @Column(length = 32, columnDefinition = "VARCHAR(15) COMMENT '手机号'")
    private String phoneNumber;

    @Column(length = 32, columnDefinition = "TINYINT(1) COMMENT '手机号是否激活'")
    private Boolean phoneNumberActiveStatus;

    @Column(length = 32, columnDefinition = "VARCHAR(512) COMMENT '头像'")
    private String profilePhoto;

    @Column(length = 32, columnDefinition = "VARCHAR(512) COMMENT '密码'")
    private String password;

    @Column(length = 32, columnDefinition = "TINYINT(1) DEFAULT 1 COMMENT '用户状态 1>正常, 2>冻结, 3>黑名单'")
    private Integer userStatus;

    @Column(length = 32, columnDefinition = "DATETIME COMMENT '注册时间'")
    private LocalDateTime createTime;

    @Column(length = 32, columnDefinition = "DATETIME COMMENT '上次登录时间'")
    private LocalDateTime lastOnlineTime;

    @Column(length = 32, columnDefinition = "TINYINT(1) COMMENT '用户类型'")
    private Integer userType;

    /**
     * 用户状态可选值
     */
    public interface UserStatus {

        /**
         * 正常, 冻结, 黑名单
         */
        Integer NORMAL = 1, COOLDOWN = 2, BLACK = 3;
    }

}
