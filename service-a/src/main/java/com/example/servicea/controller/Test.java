package com.example.servicea.controller;
import com.example.common.bo.TestRequestBo;
import com.example.servicea.FeginService.ServiceBFeginService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @Autowired
    private ServiceBFeginService serviceBFeginService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/rpc")
    public String rpc(TestRequestBo testRequestBo){
        String rpc = serviceBFeginService.rpc(testRequestBo);
        return rpc;
    }

    @PostMapping("/fallback")
    public String fallback(){
        return serviceBFeginService.fallback();
    }

    @PostMapping("/err")
    public String err(){
        return 1/0+"";
    }

    @PostMapping("/mq")
    public String mq(){
        rabbitTemplate.convertAndSend("top_exchange","");
        return 1/0+"";
    }


}
