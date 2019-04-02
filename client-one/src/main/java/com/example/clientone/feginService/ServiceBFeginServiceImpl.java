package com.example.clientone.feginService;


import com.example.common.model.clientone.query.TestQuery;
import com.example.demobase.core.Result;
import com.example.demobase.core.ResultCode;
import com.example.demobase.exception.DemoException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ServiceBFeginServiceImpl implements ServiceBFeginService {

    @Override
    @HystrixCommand(groupKey = "service-b",commandKey = "default" ,threadPoolKey = "service-b")
    public String rpc(TestQuery testRequestBo) {
        return "rpc fallback";
    }

    @Override
    @HystrixCommand(groupKey = "service-b",commandKey = "default" ,threadPoolKey = "service-b")
    public String fallback() {
        return "b服务异常";
    }

    @Override
    @HystrixCommand(groupKey = "service-b",commandKey = "default" ,threadPoolKey = "service-b")
    public Result<String> echo(String a, int b) {
        throw new DemoException(ResultCode.DB_ERROR);
    }
}
