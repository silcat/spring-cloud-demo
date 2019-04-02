package com.example.clientone.feginService;

import com.example.common.model.clientone.query.TestQuery;
import com.example.common.model.clienttwo1.dto.StorageTbl;
import com.example.demobase.core.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Primary
@FeignClient(name ="service-b", fallback = ServiceBFeginServiceImpl.class )
public interface ServiceBFeginService {

    @PostMapping(value ="/rpc")
    String rpc(TestQuery testRequestBo);

    @PostMapping(value ="/fallback")
    String fallback()
            ;
    @PostMapping(value ="/echo")
    Result<String> echo(StorageTbl storageTbl);

}
