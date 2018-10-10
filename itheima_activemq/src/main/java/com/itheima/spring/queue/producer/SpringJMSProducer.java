package com.itheima.spring.queue.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
public class SpringJMSProducer {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Destination queueTextDestination;

    public void send(final String message){

        //发送消息
        //参数说明：1、指定消息类型的名称；2、创建Message消息对象
        jmsTemplate.send(queueTextDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }

}
