package com.yaoyyy.rubbish.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 *
 * 响应通用包装工具类
 *
 * @author YaoYY
 */
@Accessors(chain = true)
@Getter
@Setter
public class R<T> {

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 状态（成功/失败）
     */
    private Boolean status;

    /**
     * 消息
     */
    private String msg;

    /**
     * 数据
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    /**
     * 私有空参构造器
     */
    private R() {}

    /**
     * 一些构造器！！！
     */
    public R(Integer code, Boolean status) {
        this.code = code;
        this.status = status;
    }

    public R(Integer code, Boolean status, String msg) {
        this.code = code;
        this.status = status;
        this.msg = msg;
    }

    public R(Integer code, Boolean status, T data) {
        this.code = code;
        this.status = status;
        this.data = data;
    }

    public R(Integer code, Boolean status, String msg, T data) {
        this.code = code;
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 静态方法
     */
    public static R ok() {
        return new R(1, true);
    }

    public static <T> R<T> ok(T t) {
        return new R<T>(1, true, t);
    }

    public static <T> R<T> ok(String msg, T t) {
        return new R<T>(1, true, msg, t);
    }

    public static R error(Integer code) {
        return new R(code, false);
    }

    public static R error(Integer code, String msg) {
        return new R(code, false, msg);
    }

    public static R error(String msg) {
        return new R(-1, false, msg);
    }

    @Override
    public String toString() {
        String jsonStr = null;
        try {
            jsonStr = new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }
}
