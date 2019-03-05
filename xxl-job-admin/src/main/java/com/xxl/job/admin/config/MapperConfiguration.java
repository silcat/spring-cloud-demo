package com.renrendai.loan.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * @ClassName MapperConfiguration
 * @Description
 * @Author zhangkuan
 * @Date 2018/7/25 10:51
 */
@Configuration
@AutoConfigureAfter(MybatisConfiguration.class)
public class MapperConfiguration implements EnvironmentAware {

    private RelaxedPropertyResolver propertyResolver;

    private String basePackage;

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(Environment environment){

        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage(basePackage);
        return mapperScannerConfigurer;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment, null);
        this.basePackage = propertyResolver.getProperty("mybatis.basepackage");
    }
}
