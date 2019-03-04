package com.example.common.core;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 统一API响应结果封装
 */
@Builder
public class Result<T> {

    /**
     * 对外返回的对象
     */
    private T data;

    /**
     * 返回状态码
     */
    private int code = ResultCode.SERVER_UNKONW_ERROR.code;

    /**
     * 返回消息
     */
    private String msg = ResultCode.SERVER_UNKONW_ERROR.msg;

    public Result() {
        super();
    }

    public Result(ResultCode status) {
        super();
        this.code = status.code;
        this.msg = status.msg;
    }

    public Result(ResultCode status, String message) {
        super();
        this.code = status.code;
        this.msg = message;
    }

    public Result(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public Result(T data, int code, String msg) {
        this(code, msg);
        this.data = data;
    }

    public Result setCode(ResultCode resultCode) {
        this.code = resultCode.code;
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Result setMsg(String message) {
        this.msg = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result setData(T data) {
        this.data = data;
        return this;
    }
}
