package com.example.common.core;

import lombok.Getter;


@Getter
public enum ResultCode {
    FAILED(-1, "未知错误"),
    SUCCESS(0, "SUCCESS"),
    MISS_PARAM(400, "参数异常"),
    SERVER_UNKONW_ERROR(500, "服务器开小差了,请稍后再试"),
    REMOTE_SERVER_ERROR(502, "远程服务调用失败"),
    NO_FUND(503,"请求接口地址不存在"),
    DB_ERROR(10000, "DB操作失败"),
    PARAM_NOT_EXIST(1002, "必填字段不存在"),
    THIRD_PARTY_ERROR(1102, "服务器开小差了，请稍后重试"),
    PARAM_VALUE_ERROR(1104, "参数错误"),
    REQUEST_ERROR(1105, "错误请求"),
    ENUM_ERROR(1107, "enum值错误");


    public final int code;
    public final String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResultCode findStatusByCode(int code) {
        for (ResultCode status : ResultCode.values()) {
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }


}
