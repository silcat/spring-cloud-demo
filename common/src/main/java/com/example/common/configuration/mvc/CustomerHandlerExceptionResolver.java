package com.example.common.configuration.mvc;

import com.alibaba.fastjson.JSON;
import com.example.common.core.Result;
import com.example.common.core.ResultCode;
import com.example.common.model.exception.DemoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;

/**
 * 统一异常处理类
 */
@Slf4j
public class CustomerHandlerExceptionResolver extends AbstractHandlerExceptionResolver {
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
       //包装异常结果
        Result result = this.parseException(e);

        //打印异常结果
        String message;
        if (handler instanceof HandlerMethod) {
            message = String.format("接口 [%s] 异常，异常信息：%s",
                    request.getRequestURI(),
                    result.getMsg());
        } else {
            message = e.getMessage();
        }
        log.error(message, e);

        //返回异常结果
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(ResultCode.SUCCESS.code);
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }

        return new ModelAndView();
    }
    /**
     * 处理异常
     *
     * @param e
     * @return
     */
    private Result parseException(Exception e) {
        Result result = new Result();
        //业务失败的异常
        if (e instanceof DemoException) {
            result = Result.builder()
                    .code(((DemoException) e).getErrorCode())
                    .msg(((DemoException) e).getErrorMsg())
                    .build();
            //URL请求错误异常
        } else if (e instanceof NoHandlerFoundException) {
            result = Result.builder()
                    .code(ResultCode.NO_FUND.code)
                    .msg(ResultCode.NO_FUND.msg)
                    .build();
            //参数校验错误异常
        } else if (e instanceof MethodArgumentNotValidException) {
            result = Result.builder()
                    .code(ResultCode.PARAM_VALUE_ERROR.code)
                    .msg(e.getMessage())
                    .build();
        } else if (e instanceof ConstraintViolationException) {
            result = Result.builder()
                    .code(ResultCode.PARAM_VALUE_ERROR.code)
                    .msg(e.getMessage())
                    .build();
        } else if (e instanceof BindException) {
            String fieldName = ((BindException) e).getFieldErrors().get(0).getField();
            String defaultMessage  = ((BindException) e).getFieldErrors().get(0).getDefaultMessage();
            result = Result.builder()
                    .code(ResultCode.PARAM_VALUE_ERROR.code)
                    .msg(fieldName+defaultMessage)
                    .build();
            //系统内部异常
        } else if (e instanceof ServletException) {
            result = Result.builder()
                    .code(ResultCode.SERVER_UNKONW_ERROR.code)
                    .msg(ResultCode.SERVER_UNKONW_ERROR.msg)
                    .build();
            //其他异常
        } else {
            result = Result.builder()
                    .code(ResultCode.FAILED.code)
                    .msg(ResultCode.FAILED.msg)
                    .build();
        }
        return result;
    }
}
