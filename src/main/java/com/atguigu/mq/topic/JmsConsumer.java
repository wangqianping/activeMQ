package com.atguigu.mq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JmsConsumer {
    private static final String ACTIVE_URL = "tcp://192.168.199.129:61616";
    private static final String TOPIC_NAME = "demo1";

    public static void main(String[] args) throws JMSException, IOException {
        //1.创建连接工厂，按照给定的URL地址，采用默认的用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVE_URL);
        //2.通过连接工厂获得连接对象Connection
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3.通过连接对象获取Session:两个参数 事务/签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地，指定是队列还是主题
        Topic topic = session.createTopic(TOPIC_NAME);
        //5.创建消息消费者
        MessageConsumer consumer = session.createConsumer(topic);
        //6.接收消息方式一：通过receive
//        while (true){
//            TextMessage message = (TextMessage) consumer.receive();
//            if(null!=message){
//                System.out.println(message.getText());
//            }else {
//                break;
//            }
//        }

        //6.接收消息方式二：通过Listener
        consumer.setMessageListener(message -> {
            if (message != null && message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        System.in.read();

        //7.关闭资源
        consumer.close();
        session.close();
        connection.close();

    }
}
