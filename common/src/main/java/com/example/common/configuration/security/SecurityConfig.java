package com.example.common.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.ManagementServerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@ConditionalOnProperty(value = "management.security.enabled",havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(ManagementServerProperties.class)
public  class SecurityConfig extends WebSecurityConfigurerAdapter  {
    @Autowired
    private ManagementServerProperties managementServerProperties;
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers(managementServerProperties.getContextPath()+"/**").authenticated();
        http.httpBasic();
        http.csrf().disable();
    }
}