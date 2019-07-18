package com.yaoyyy.rubbish.common;

/**
 * 响应码枚举
 */
public enum CodeEnum {

    REQUIRE_LOGIN(403, "未登录"),
    AUTH_FAIL(401, "oauth认证失败"),

    SUCCESS(1, "成功"),
    ERROR(-1, "失败");

    private Integer code;
    private String msg;

    CodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
