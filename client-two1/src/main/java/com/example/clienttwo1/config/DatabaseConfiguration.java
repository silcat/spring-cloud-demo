package com.example.clienttwo1.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.fescar.rm.datasource.DataSourceProxy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * fescar配置
 */
@Configuration
public class DatabaseConfiguration {
    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DruidDataSource druidDataSource(){
        return DruidDataSourceBuilder.create().build();
    }
    @Bean
    public DataSourceProxy dataSourceProxy(DruidDataSource druidDataSource) {
        return new DataSourceProxy(druidDataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSourceProxy dataSourceProxy) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceProxy);
        return jdbcTemplate;

    }
}
