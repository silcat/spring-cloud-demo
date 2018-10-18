package com.example.servicea.controller;

import com.example.common.bo.TestRequestBo;
import com.example.servicea.FeginService.ServiceBFeginService;
import com.example.servicea.FeginService.ServiceBNoFallbackFeginService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.trace.Trace;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.annotation.ContinueSpan;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class Test {
@Autowired
private Tracer tracer;
    @Value("${test}")
    private String test;

    @Autowired
    private ServiceBFeginService serviceBFeginService;

    @Autowired
    private ServiceBNoFallbackFeginService serviceBNoFallbackFeginService;

    @PostMapping("/rpc")
    public String rpc(TestRequestBo testRequestBo){
        String rpc = serviceBFeginService.rpc(testRequestBo);
        return rpc;
    }

    @PostMapping("/fallback")
    public String fallback(){
        return serviceBFeginService.fallback();
    }

    @PostMapping("/config")
    public String config(){
        return serviceBFeginService.fallback();
    }

    @PostMapping("/retry")
    public String retry(){
        String fallback="";
        try {
            fallback = serviceBNoFallbackFeginService.fallback();
        }catch (Exception e){
            Span newSpan = this.tracer.createSpan("errSpan");
            try {
                // ...
                // You can tag a span
                this.tracer.addTag("msg",e.toString() );
                // You can log an event on a span
                newSpan.logEvent("errSpan");
            } finally {
                this.tracer.close(newSpan);
            }
            throw e;
        }
        return fallback;
    }




}
