package com.atguigu.mq.spring;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class SpringConsumer {

    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ClassPathXmlApplicationContext appletContext = new ClassPathXmlApplicationContext("application.xml");
        SpringConsumer springConsumer = appletContext.getBean(SpringConsumer.class);
        String message = (String) springConsumer.jmsTemplate.receiveAndConvert();
        System.out.println("接收到消息是："+message);

    }

}
