package com.example.servicea.FeginService;

import com.example.common.bo.TestRequestBo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ServiceBFeginServiceImpl implements ServiceBFeginService {

    @Override
    @HystrixCommand(groupKey = "cb1",commandKey = "cb" )
    public String rpc(TestRequestBo testRequestBo) {
        return "rpc fallback";
    }

    @Override
    @HystrixCommand(groupKey = "cb1",commandKey = "cb1" )
    public String fallback() {
        return "b服务异常";
    }
}
