package com.example.servicea.configuration.rabbiteMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

/**
 * 如果消息没有到exchange,则confirm回调,ack=false
 * 如果消息到达exchange,则confirm回调,ack=true
 */
@Component("confirmCallBackListener")
public class ConfirmCallbackLister implements RabbitTemplate.ConfirmCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        System.out.println("confirm--:correlationData:"+correlationData+",ack:"+b+",cause:"+s);
    }
}
