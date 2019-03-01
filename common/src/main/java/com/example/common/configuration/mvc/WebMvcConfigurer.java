package com.example.common.configuration.mvc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.example.common.core.Result;
import com.example.common.core.ResultCode;
import com.example.common.model.exception.DemoException;
import com.example.common.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.BindException;
import org.springframework.validation.Validator;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

@Configuration
@Slf4j
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    /**
     * 使用阿里 FastJson 作为JSON MessageConverter
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        //保留空的字段
        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue);
        //SerializerFeature.WriteNullStringAsEmpty,//String null -> ""
        //SerializerFeature.WriteNullNumberAsZero//Number null -> 0
        // 按需配置，更多参考FastJson文档哈
        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        converters.add(converter);
    }


    /**
     * 统一异常处理
     *
     * @param exceptionResolvers
     */
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add((HttpServletRequest request, HttpServletResponse response, Object o, Exception e)
                -> {
            Result result = parseException(e);
            String message;
            if (o instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) o;
                message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
                        request.getRequestURI(),
                        handlerMethod.getBean().getClass().getName(),
                        handlerMethod.getMethod().getName(),
                        e.getMessage());
            } else {
                message = e.getMessage();
            }
            log.error(message, e);
            responseResult(response, result);
            return new ModelAndView();
        });
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
            result = result.builder()
                    .code(((DemoException) e).getErrorCode())
                    .msg(((DemoException) e).getErrorMsg())
                    .build();
            //URL请求错误异常
        } else if (e instanceof NoHandlerFoundException) {
            result = result.builder()
                    .code(ResultCode.NO_FUND.code)
                    .msg(ResultCode.NO_FUND.msg)
                    .build();
            //参数校验错误异常
        } else if (e instanceof MethodArgumentNotValidException) {
            result = result.builder()
                    .code(ResultCode.PARAM_VALUE_ERROR.code)
                    .msg(e.getMessage())
                    .build();
        } else if (e instanceof ConstraintViolationException) {
            result = result.builder()
                    .code(ResultCode.PARAM_VALUE_ERROR.code)
                    .msg(e.getMessage())
                    .build();
        } else if (e instanceof BindException) {
            result = result.builder()
                    .code(ResultCode.PARAM_VALUE_ERROR.code)
                    .msg(e.getMessage())
                    .build();
            //系统内部异常
        } else if (e instanceof ServletException) {
            result = result.builder()
                    .code(ResultCode.SERVER_UNKONW_ERROR.code)
                    .msg(ResultCode.SERVER_UNKONW_ERROR.msg)
                    .build();
            //其他异常
        } else {
            result = result.builder()
                    .code(ResultCode.FAILED.code)
                    .msg(ResultCode.FAILED.msg)
                    .build();
        }
        return result;
    }

    /**
     * 返回异常结果
     *
     * @param response
     * @param result
     */
    private void responseResult(HttpServletResponse response, Result result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                String requestParams = getRequestParams(request);
                log.warn(requestParams);
                return true;
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
                String responseBody = getResponseBody(response);
                log.warn(responseBody);
            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

            }
        });
    }


    private String getRequestParams(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();
        sb.append("request:")
                .append(request.getRequestURI())
                .append(", params:{");
        Enumeration<String> params = request.getParameterNames();
        int index = 0;
        while (params.hasMoreElements()) {
            String name = params.nextElement();
            String val = Optional.ofNullable(request.getParameter(name)).isPresent() ? request.getParameter(name):"null";
            if (index > 0){
                sb.append(",");
            }
            sb.append(name).append(": ").append(val);
            index++;
        }
        sb.append("}");
        return sb.toString();
    }

    private String getResponseBody(HttpServletResponse response) {
        ContentCachingResponseWrapper wrapperResponse = new ContentCachingResponseWrapper(response);
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(wrapperResponse, ContentCachingResponseWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                String payload;
                try {
                    payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException e) {
                    payload = "[unknown]";
                }
                return payload;
            }
        }
        return "";
    }
}
