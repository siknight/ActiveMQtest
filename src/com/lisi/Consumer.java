package com.lisi;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumer {
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;//使用默认的用户名
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;//默认的密码
    private static final String URL = ActiveMQConnection.DEFAULT_BROKER_URL;//默认的连接地址

    public static void main(String[] args) {
        ConnectionFactory connectionFactory ;//连接工厂
        Connection connection;//连接
        Session session;//会话
        Destination destination;//消息目标
        MessageConsumer messageConsumer;//消息的消费者

        connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, URL);//初始化连接工厂

        try {
            connection = connectionFactory.createConnection();//创建连接
            connection.start();//启动连接信息
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("java12");//选择协议主题，并且初始化主题名称
            messageConsumer = session.createConsumer(destination);//创建消费者

            while(true){
//              TextMessage tx = session.createTextMessage();//接收消息
                TextMessage tx = (TextMessage) messageConsumer.receive(1000000);//100S
                if (tx != null) {
                    System.out.println("消费者接收消息内容为："+tx.getText());
                }else{
                    break;
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }


    }
}
