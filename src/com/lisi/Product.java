package com.lisi;

import org.apache.activemq.ActiveMQConnection;


import org.apache.activemq.ActiveMQConnectionFactory;




import javax.jms.*;


public class Product {
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;//使用默认的用户名
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;//默认的密码
    private static final String URL = ActiveMQConnection.DEFAULT_BROKER_URL;//默认的连接地址

    public static void main(String[] args) {
        ConnectionFactory connectionFactory ;//连接工厂
        Connection connection;//连接
        Session session;//会话
        Destination destination;//消息目标
        MessageProducer messageProducer;//消息的生产者

        connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, URL);//初始化连接工厂
        try {

              connection  =  connectionFactory.createConnection();//创建连接
              connection.start();//启动连接信息

            /**
             * boolean参数1：如果是true，则代表开启事务。参数2则可以取以下前3种
             *                         如果是false，则代表不开启事务。参数2则可以取以下4种
             *
             * int 参数2：AUTO_ACKNOWLEDGE---客户端无需做任何副本操作，就可以获取消息信息(自动装配模式)
             *          CLIENT_ACKNOWLEDGE---客户端需要做操作确认，才可以获取消息
             *          DUPS_OK_ACKNOWLEDGE---是无需任何副本操作
             *          SESSION_TRANSACTED----只有在第一个参数使用false的时候，才可以使用
             *
             */
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("java12");//选择协议主题，并且初始化主题名称
            messageProducer = session.createProducer(destination);//创建生产者
            sendMessage(messageProducer,session);//发送消息
            session.commit();//提交消息到MQ容器中
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    private static void sendMessage(MessageProducer messageProducer, Session session) throws JMSException {
        for (int i = 0; i < 10; i++) {
            TextMessage tx = session.createTextMessage("ActiveMQ 发送消息" + i);
            System.out.println("生产者发送消息内容为：" + tx.getText());
            messageProducer.send(tx);
        }
    }
}
