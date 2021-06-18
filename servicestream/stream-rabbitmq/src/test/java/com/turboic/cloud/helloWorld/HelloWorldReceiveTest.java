package com.turboic.cloud.helloWorld;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试rabbitmq hello world 模式
 * 只能使用main方法测试监听收消息
 */
public class HelloWorldReceiveTest
{
    private static final String QUEUE_NAME = "helloworld";
    private static final Logger logger = LoggerFactory.getLogger(HelloWorldReceiveTest.class);
    private static final String host = "10.10.10.10";
    private static final int port = 5672;
    private static final String username = "zhxl";
    private static final String password = "zhxl";

    public static void testReceive(){
        try{
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            factory.setPort(port);
            factory.setUsername(username);
            factory.setPassword(password);
            factory.setVirtualHost("/");
            Connection connection = factory.newConnection();
            /**
             * 如果消费者先于生产者启动，消息可能无法监听到队列，那么就需要下面不注释的代码了
             */
            /*Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");*/

            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            /***传递回调 ***/
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                logger.error(" [x] Received '" + message + "'");
            };
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
        }catch(Exception e){
            logger.error("消息接收失败:{}",e);
        }
    }
    public static void main(String [] args){
        testReceive();
    }
}
