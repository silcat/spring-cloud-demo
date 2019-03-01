package com.example.common.model.exception;

import com.example.common.core.ResultCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class DemoException extends RuntimeException {
    private static final long serialVersionUID = 2195068675053227207L;

    private int errorCode = ResultCode.SERVER_UNKONW_ERROR.code;

    private String errorMsg = "unknown error";

    public DemoException() {
        super();
    }

    public DemoException(String message, int errorCode, String errorMsg) {
        super(message);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }


    public DemoException(int code, String message) {
        super();
        this.errorCode = code;
        this.errorMsg = message;
    }

    public DemoException(String message, ResultCode status) {
        super(message);
        this.errorCode = status.code;
        this.errorMsg = StringUtils.isEmpty(message) ? status.msg : message;
    }


    public DemoException(ResultCode errorInfo) {
        this(errorInfo.getCode(), errorInfo.getMsg());
    }

    public DemoException(ResultCode errorInfo, String message) {
        this(message, errorInfo);
    }


}
