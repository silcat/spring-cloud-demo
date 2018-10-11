package com.example.servicea.FeginService;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Primary
@FeignClient(name ="service-b", fallback = ServiceBFeginServiceImpl.class )
public interface ServiceBFeginService {

    @PostMapping(value ="/rpc")
    String rpc();

    @GetMapping(value ="/fallback")
    String fallback();

}
