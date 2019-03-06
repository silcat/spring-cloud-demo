package com.example.common.configuration.mvc;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.ManagementServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * 打印响应日志
 */
@Configuration
@Slf4j
@EnableConfigurationProperties(ManagementServerProperties.class)
public class CustomerWebMvcSupportConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    private ManagementServerProperties managementServerProperties;
    /**
     * 打印响应日志
     * @return
     */
    @Override
    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter adapter = super.requestMappingHandlerAdapter();
        adapter.setResponseBodyAdvice(Lists.newArrayList(new CustomerResponseBodyAdvisor(managementServerProperties)));
        return adapter;
    }
}
