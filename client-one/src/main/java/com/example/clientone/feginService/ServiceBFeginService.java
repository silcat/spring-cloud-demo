package com.example.clientone.feginService;

import com.example.common.model.clientone.query.TestQuery;
import com.example.demobase.core.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PostMapping;

@Primary
@FeignClient(name ="service-b", fallback = ServiceBFeginServiceImpl.class )
public interface ServiceBFeginService {

    @PostMapping(value ="/rpc")
    String rpc(TestQuery testRequestBo);

    @PostMapping(value ="/fallback")
    String fallback()
            ;
    @PostMapping(value ="/echo")
    Result<String> echo(String a, int b);

}
