package com.example.getway.configuration;

import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ZipkinConfiguration {

    /**
     * 对所有请求进行采样（默认采样器为PercentageBasedSampler）
     * @return
     */
    @Bean
    public Sampler defaultSampler() {
        return new AlwaysSampler();
    }

}
