package com.turboic.cloud.publishSubscribe;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试rabbitmq Publish/Subscribe 模式
 * 只能使用main方法测试监听收消息
 *  Publish/Subscribe
 */
public class publishSubscribeReceiveTest
{
    private static final String EXCHANGE_NAME = "logs";
    private static final Logger logger = LoggerFactory.getLogger(publishSubscribeReceiveTest.class);
    private static final String host = "10.10.10.10";
    private static final int port = 5672;
    private static final String username = "zhxl";
    private static final String password = "zhxl";
    public static void main(String [] args){
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            factory.setPort(port);
            factory.setUsername(username);
            factory.setPassword(password);
            factory.setVirtualHost("/");
            Connection connection = factory.newConnection();
            final Channel channel = connection.createChannel();
            //声明交换机名称及类型为fanout
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            String queueName = channel.queueDeclare().getQueue();
            //将队列和交换机名称绑定
            channel.queueBind(queueName, EXCHANGE_NAME, "");
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            //消息监听回调
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            };
            //基本消费
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });

        }catch(Exception e) {
            logger.error("消息接收失败:{}",e);
        }
    }
}
