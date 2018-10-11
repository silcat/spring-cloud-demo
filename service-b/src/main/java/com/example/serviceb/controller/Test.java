package com.example.serviceb.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @PostMapping("/rpc")
    public String rpc(){
       return "来自B1的消息";
    }

    @PostMapping("/fallback")
    public String fallback(){
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "B1 FALLBACK";
    }

}
