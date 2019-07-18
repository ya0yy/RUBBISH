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
    private CodeEnum code;

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
    public R(CodeEnum code, Boolean status) {
        this.code = code;
        this.status = status;
    }

    public R(CodeEnum code, Boolean status, String msg) {
        this.code = code;
        this.status = status;
        this.msg = msg;
    }

    public R(CodeEnum code, Boolean status, T data) {
        this.code = code;
        this.status = status;
        this.data = data;
    }

    public R(CodeEnum code, Boolean status, String msg, T data) {
        this.code = code;
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 静态方法
     */
    public static R ok() {
        return new R(CodeEnum.SUCCESS, true);
    }

    public static <T> R<T> ok(T t) {
        return new R<>(CodeEnum.SUCCESS, true, t);
    }

    public static <T> R<T> ok(String msg, T t) {
        return new R<>(CodeEnum.SUCCESS, true, msg, t);
    }

    public static <T> R<T> error() {
        return R.error(CodeEnum.ERROR);
    }

    public static <T> R<T> error(CodeEnum code) {
        return new R<T>(code, false).setMsg(code.getMsg());
    }

    public static R error(CodeEnum code, String msg) {
        return new R(code, false, msg);
    }

    public static R error(String msg) {
        return new R(CodeEnum.ERROR, false, msg);
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
