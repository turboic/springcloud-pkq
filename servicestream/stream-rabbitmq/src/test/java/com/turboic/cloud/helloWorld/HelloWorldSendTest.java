package com.turboic.cloud.helloWorld;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 测试rabbitmq hello world 模式
 *
 * 测试通过
 */
public class HelloWorldSendTest
{
    private static final String QUEUE_NAME = "helloworld";
    private static final Logger logger = LoggerFactory.getLogger(HelloWorldSendTest.class);
    private static final String host = "10.10.10.10";
    private static final int port = 5672;
    private static final String username = "zhxl";
    private static final String password = "zhxl";

    /***
     * 列出所有的队列名称
     * sudo rabbitmqctl list_queues
     * 仅仅通过队列进行数据交换
     */
    @Test
    public void testSend(){
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            factory.setPort(port);
            factory.setUsername(username);
            factory.setPassword(password);
            factory.setVirtualHost("/");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            /**
             * 声明队列
             */
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }catch(Exception e){
            logger.error("消息发送失败:{}",e);
        }
    }
}
