package com.example.common.configuration.xxjob;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * xxjob分布式任务管理器
 *
 */
@ConfigurationProperties("xxl.job.admin")
@Getter
@Setter
public class XxlJobProperties {
    private String addresses;
    private String appname;
    private String ip;
    private int port;
    private String logpath;

}
