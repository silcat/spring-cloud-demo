package com.example.servicea.configuration.rabbiteMQ;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class rabbiteMQConfig {
    @Value("${spring.rabbitmq.queue.mes1}")
    private String mes1;

    @Value("${spring.rabbitmq.queue.mes2}")
    private String mes2;

    @Value("${spring.rabbitmq.exchange.topic}")
    private String exchangetop;


    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,ConfirmCallbackLister confirmCallbackLister,ReturnCallbackLister returnCallbackLister) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConfirmCallback(confirmCallbackLister);
        rabbitTemplate.setReturnCallback(returnCallbackLister);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Queue mes1(){
        // 是否持久化
        boolean durable = true;
        // 仅创建者可以使用的私有队列，断开后自动删除
        boolean exclusive = false;
        // 当所有消费客户端连接断开后，是否自动删除队列
        boolean autoDelete = false;
        return new Queue(mes1, durable, exclusive, autoDelete);
    }

    @Bean
    public Queue mes2(){
        // 是否持久化
        boolean durable = true;
        // 仅创建者可以使用的私有队列，断开后自动删除
        boolean exclusive = false;
        // 当所有消费客户端连接断开后，是否自动删除队列
        boolean autoDelete = false;
        return new Queue(mes2, durable, exclusive, autoDelete);
    }
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(exchangetop);
    }

    @Bean
    Binding bindingExchangeMessage(Queue mes1, TopicExchange exchange) {
        return BindingBuilder.bind(mes1).to(exchange).with("topic.message");

    }
}

