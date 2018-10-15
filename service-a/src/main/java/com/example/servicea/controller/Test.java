package com.example.servicea.controller;

import com.example.common.bo.TestRequestBo;
import com.example.servicea.FeginService.ServiceBFeginService;
import com.example.servicea.FeginService.ServiceBNoFallbackFeginService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class Test {

    @Value("${test}")
    private String test;

    @Autowired
    private ServiceBFeginService serviceBFeginService;

    @Autowired
    private ServiceBNoFallbackFeginService serviceBNoFallbackFeginService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/rpc")
    public String rpc(TestRequestBo testRequestBo){
        String rpc = serviceBFeginService.rpc(testRequestBo);
        return rpc;
    }

    @PostMapping("/fallback")
    public String fallback(){
        return test;
    }

    @PostMapping("/config")
    public String config(){
        return serviceBFeginService.fallback();
    }

    @PostMapping("/retry")
    public String retry(){
        return serviceBNoFallbackFeginService.fallback();
    }

    @PostMapping("/mq")
    public String mq(){
        rabbitTemplate.convertAndSend("top_exchange","topic.message","message");
        return "sucee";
    }


}
