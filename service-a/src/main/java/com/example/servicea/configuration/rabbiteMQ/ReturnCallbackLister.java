package com.example.servicea.configuration.rabbiteMQ;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

/**
 * exchange到queue失败,则回调return(需设置mandatory=true,否则不回回调,消息就丢了)
 */
@Component("returnCallBackListener")
public class ReturnCallbackLister implements RabbitTemplate.ReturnCallback {

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println("return--message:" + new String(message.getBody()) + ",replyCode:" + i + ",replyText:" + s + ",exchange:" + s1 + ",routingKey:" + s2);
    }
}
