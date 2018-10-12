package com.example.servicea.configuration.rabbiteMQ;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class rabbiteMQConfig {

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
    public SimpleMessageListenerContainer messageContainer(ConnectionFactory connectionFactory) {
		/*Queue[] q = new Queue[queues.split(",").length];
		for (int i = 0; i < queues.split(",").length; i++) {
			q[i] = new Queue(queues.split(",")[i]);
		}*/
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
//        container.setQueues(queue());
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener(new ChannelAwareMessageListener() {

            public void onMessage(Message message, com.rabbitmq.client.Channel channel) throws Exception {
                try {
                    System.out.println(
                            "消费端接收到消息:" + message.getMessageProperties() + ":" + new String(message.getBody()));
                    System.out.println("topic:" + message.getMessageProperties().getReceivedRoutingKey());
                    // deliveryTag是消息传送的次数，我这里是为了让消息队列的第一个消息到达的时候抛出异常，处理异常让消息重新回到队列，然后再次抛出异常，处理异常拒绝让消息重回队列
					/*if (message.getMessageProperties().getDeliveryTag() == 1
							|| message.getMessageProperties().getDeliveryTag() == 2) {
						throw new Exception();
					}*/

                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // false只确认当前一个消息收到，true确认所有consumer获得的消息
                } catch (Exception e) {
                    e.printStackTrace();

                    if (message.getMessageProperties().getRedelivered()) {
                        System.out.println("消息已重复处理失败,拒绝再次接收...");
                        channel.basicReject(message.getMessageProperties().getDeliveryTag(), true); // 拒绝消息
                    } else {
                        System.out.println("消息即将再次返回队列处理...");
                        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true); // requeue为是否重新回到队列
                    }
                }
            }
        });
        return container;
    }
}

