package com.example.common.configuration.mvc;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 *
 */
@Configuration
@Slf4j
public class CustomerWebMvcSupportConfiguration extends WebMvcConfigurationSupport {

    /**
     * 打印（ajax请求）响应日志
     * @return
     */
    @Override
    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter adapter = super.requestMappingHandlerAdapter();
        adapter.setResponseBodyAdvice(Lists.newArrayList(new CustomerResponseBodyAdvisor()));
        return adapter;
    }
}
