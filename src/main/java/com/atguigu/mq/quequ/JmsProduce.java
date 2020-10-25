package com.atguigu.mq.quequ;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduce {

    private static final String ACTIVE_URL = "tcp://192.168.199.129:61616";
    private static final String QUEUE_NAME = "demo1";

    public static void main(String[] args) throws JMSException {
        //1.创建连接工厂，按照给定的URL地址，采用默认的用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVE_URL);
        //2.通过连接工厂获得连接对象Connection并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3.通过连接对象获取Session:两个参数 事务/签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地，指定是队列还是主题
        Queue queue = session.createQueue(QUEUE_NAME);
        //5.创建消息的生产者producer
        MessageProducer producer = session.createProducer(queue);
        //6.通过消息生产者创建消息发送到MQ中去
        for(int i=1;i<=3;i++){
            //session负责创建，producer负责发送
            TextMessage textMessage = session.createTextMessage("msg----" + i);
            producer.send(textMessage);
        }
        //7.关闭资源
        producer.close();
        session.close();
        connection.close();

        System.out.println("*****消息成功发送到消息队列中去********");


    }
}
