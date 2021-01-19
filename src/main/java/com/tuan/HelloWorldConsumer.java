package com.tuan;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq 消费者
 * @author tuan
 * @date 2021/01/19 13:53
 **/
public class HelloWorldConsumer {
    private final static String QUEUE_NAME = "hello";
    public static void main(String[] args) throws IOException, TimeoutException, TimeoutException {

        ConnectionFactory cf = new ConnectionFactory();
        //rabbitmq监听IP
        cf.setHost("192.168.10.136");
        //rabbitmq默认监听端口，注意要记得开启端口
        cf.setPort(5672);

        //设置访问的用户
        cf.setUsername("guest");
        cf.setPassword("guest");
        //建立连接
        Connection conn = cf.newConnection();
        //创建消息通道
        Channel channel = conn.createChannel();

        //创建hello队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" Waiting for msg....");
        //创建消费者，并接受消息
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("Received is = '" + msg + "'");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }

}
