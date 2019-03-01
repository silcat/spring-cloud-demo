package com.example.common.configuration.xxjob;


import com.xxl.job.core.executor.XxlJobExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

/**
 * xxjob分布式任务管理器
 *
 */
@Slf4j
@Configuration
@ConditionalOnProperty(value = "xxl.job.admin.address",matchIfMissing = true)
@EnableConfigurationProperties(XxlJobProperties.class)
public class XxlJobConfig {

   @Autowired
   private XxlJobProperties xxlJobProperties;

    @Bean(initMethod = "start", destroyMethod = "destroy")
    public XxlJobExecutor xxlJobExecutor() throws UnknownHostException {
        log.info(">>>>>>>>>>> xxl-job config init.");

        XxlJobExecutor xxlJobExecutor = new XxlJobExecutor();
        xxlJobExecutor.setIp(xxlJobProperties.getIp());
        xxlJobExecutor.setPort(xxlJobProperties.getPort());
        xxlJobExecutor.setAppName(xxlJobProperties.getAppname());
        xxlJobExecutor.setAdminAddresses(xxlJobProperties.getAddresses());
        xxlJobExecutor.setLogPath(xxlJobProperties.getLogpath());
        return xxlJobExecutor;
    }
}
