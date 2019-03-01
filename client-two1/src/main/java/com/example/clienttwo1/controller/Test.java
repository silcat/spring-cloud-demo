package com.example.clienttwo1.controller;

import com.example.common.model.clientone.query.TestQuery;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @PostMapping("/rpc")
    @HystrixCommand
    public String rpc(TestQuery testRequestB){
       return "来自B1的消息："+testRequestB.getName()+testRequestB.getValue();
    }

    @PostMapping("/fallback")
    @HystrixCommand
    public String fallback(){
        return 1/0+"B1 FALLBACK";
    }

}
