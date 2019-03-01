package com.example.clientone.feginService;

import com.example.common.model.clientone.query.TestQuery;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Primary
@FeignClient(name ="service-b")
public interface ServiceBNoFallbackFeginService {

    @PostMapping(value ="/rpc")
    String rpc(TestQuery testRequestBo);

    @GetMapping(value ="/fallback")
    String fallback();

}
