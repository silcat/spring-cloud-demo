package com.example.servicea.FeginService;

import com.example.common.bo.TestRequestBo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.sleuth.annotation.SpanTag;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ServiceBFeginServiceImpl implements ServiceBFeginService {

    @Override
    @NewSpan("rpc.fallback.newspan.")
    public String rpc(TestRequestBo testRequestBo) {
        return "rpc fallback";
    }

    @Override
    @NewSpan("fallback.fallback.newspan.")
    public String fallback( ) {
        return "b服务异常";
    }
}
