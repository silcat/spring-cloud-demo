package com.example.servicea.FeginService;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ServiceBFeginServiceImpl implements ServiceBFeginService {

    @Override
    @HystrixCommand(groupKey = "service-b",commandKey = "default" ,threadPoolKey = "service-b")
    public String rpc() {
        return "rpc fallback";
    }

    @Override
    @HystrixCommand(groupKey = "service-b",commandKey = "default" ,threadPoolKey = "service-b")
    public String fallback() {
        return "b服务异常";
    }
}
