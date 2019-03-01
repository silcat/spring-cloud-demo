package com.example.clienttwo2.controller;
import com.example.common.model.clientone.query.TestQuery;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @PostMapping("/rpc")
    @HystrixCommand
    public String rpc(TestQuery testRequestB){
        return "来自B2的消息："+testRequestB.toString();
    }

    @PostMapping("/fallback")
    @HystrixCommand
    public String fallback() throws InterruptedException {
        return "B2 FALLBACK";
    }



}
