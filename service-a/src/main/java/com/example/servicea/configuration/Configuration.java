package com.example.servicea.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@ConfigurationProperties
@RefreshScope
@Getter
@Setter
public class Configuration  {
    @Value("${test}")
    private String test;

}