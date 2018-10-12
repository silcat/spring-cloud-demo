package com.example.servicea.controller;
import com.example.servicea.FeginService.ServiceBFeginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @Autowired
    private ServiceBFeginService serviceBFeginService;

    @PostMapping("/rpc")
    public String rpc(){
       return serviceBFeginService.rpc();
    }

    @PostMapping("/fallback")
    public String fallback(){
        return serviceBFeginService.fallback();
    }

    @PostMapping("/err")
    public String err(){
        return 1/0+"";
    }

}
