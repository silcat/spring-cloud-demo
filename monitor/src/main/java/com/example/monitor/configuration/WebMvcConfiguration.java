package com.example.monitor.configuration;

import de.codecentric.boot.admin.web.servlet.resource.PreferMinifiedFilteringResourceResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler( "/login.html")
                .addResourceLocations("classpath:/statics/")
                .resourceChain(true)
                .addResolver(new PreferMinifiedFilteringResourceResolver(".min"));

        registry.addResourceHandler( "/login.css")
                .addResourceLocations("classpath:/META-INF/spring-boot-admin-server-ui/")
                .resourceChain(true)
                .addResolver(new PreferMinifiedFilteringResourceResolver(".min"));

        registry.addResourceHandler( "/core.css")
                .addResourceLocations("classpath:/META-INF/spring-boot-admin-server-ui/")
                .resourceChain(true)
                .addResolver(new PreferMinifiedFilteringResourceResolver(".min"));


    }
}
