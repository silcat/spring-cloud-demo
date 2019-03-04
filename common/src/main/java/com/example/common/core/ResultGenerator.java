package com.example.common.core;

/**
 * 统一响应类
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static Result genSuccessResult() {
        return  Result.builder()
                .code(ResultCode.SUCCESS.code)
                .msg(DEFAULT_SUCCESS_MESSAGE)
                .build();
    }

    public static <T> Result<T> genSuccessResult(T data) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMsg(ResultCode.SUCCESS.msg)
                .setData(data);
    }

    public static Result genFailResult(String message) {
        return genFailResult(ResultCode.SERVER_UNKONW_ERROR.code , message);
    }

    public static Result genFailResult(ResultCode resultCode) {
        return genFailResult(resultCode.code,resultCode.msg);
    }

    public static Result genFailResult(int code ,String message) {
        return  Result.builder()
                .code(code)
                .msg(message)
                .build();
    }
}
