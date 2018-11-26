package com.example.clientone.feginService;

import com.example.common.bo.TestRequestBo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PostMapping;

@Primary
@FeignClient(name ="service-b", fallback = ServiceBFeginServiceImpl.class )
public interface ServiceBFeginService {

    @PostMapping(value ="/rpc")
    String rpc(TestRequestBo testRequestBo);

    @PostMapping(value ="/fallback")
    String fallback();

}
