package com.example.common.configuration.mvc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.actuate.autoconfigure.ManagementServerProperties;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Optional;

/**
 * 打印响应日志
 */
@Slf4j
public class CustomerResponseBodyAdvisor implements ResponseBodyAdvice<Object> {

    private ManagementServerProperties managementServerProperties;

    public CustomerResponseBodyAdvisor(ManagementServerProperties managementServerProperties) {
        this.managementServerProperties = managementServerProperties;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        String requestURI = servletRequest.getRequestURI();
        //actuator请求不打印日志
        if (requestURI.startsWith(managementServerProperties.getContextPath())){
            return body;
        }
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
