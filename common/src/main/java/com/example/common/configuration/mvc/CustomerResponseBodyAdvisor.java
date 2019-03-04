package com.example.common.configuration.mvc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Optional;

/**
 * 打印响应日志
 */
@Slf4j
public class CustomerResponseBodyAdvisor implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 响应值转JSON串输出到日志系统
        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        Enumeration<String> params = servletRequest.getParameterNames();
        StringBuffer requestParams = new StringBuffer();
        requestParams.append("{");
        int index = 0;
        while (params.hasMoreElements()) {
            String name = params.nextElement();
            String val = Optional.ofNullable(servletRequest.getParameter(name)).isPresent() ? servletRequest.getParameter(name):"null";
            if (index > 0){
                requestParams.append(",");
            }
            requestParams.append(name).append(": ").append(val);
            index++;
        }
        requestParams.append("}");
        log.info("response: url:{} param:{} result：{}", servletRequest.getRequestURI(),requestParams.toString(), JSON.toJSONString(body, SerializerFeature.UseSingleQuotes));
        return body;
    }
}
