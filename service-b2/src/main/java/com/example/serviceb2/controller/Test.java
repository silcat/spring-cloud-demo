package com.example.serviceb2.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @PostMapping("/rpc")
    public String rpc(){
       return "来自B2的消息";
    }

    @PostMapping("/fallback")
    public String fallback(){
        return "B2 FALLBACK";
    }

}
