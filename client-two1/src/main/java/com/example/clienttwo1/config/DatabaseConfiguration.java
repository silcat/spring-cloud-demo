package com.example.clienttwo1.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.fescar.rm.datasource.DataSourceProxy;
import com.alibaba.fescar.spring.annotation.GlobalTransactionScanner;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.application.name}")
    private String applicationId;


    @Bean
    @ConfigurationProperties("spring.datasource")
    public DruidDataSource druidDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Primary
    @Bean
    public DataSourceProxy dataSource(DruidDataSource druidDataSource) {
        return new DataSourceProxy(druidDataSource);
    }

    @Primary
    @Bean("dataSource")
    public JdbcTemplate jdbcTemplate(DataSourceProxy dataSourceProxy) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceProxy);
        return jdbcTemplate;

    }

    @Bean
    public GlobalTransactionScanner globalTransactionScanner(DataSourceProxy dataSourceProxy) {
        return new test(applicationId,"client-two-fescar-service-group");
    }


}
