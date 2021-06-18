package com.turboic.cloud.topics;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 测试rabbitmq topics 模式
 * topics
 *
 * 测试通过
 */
public class topicsSendTest
{
    private static final String EXCHANGE_NAME = "topic_logs";
    private static final Logger logger = LoggerFactory.getLogger(topicsSendTest.class);
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
             * 声明channel交换机的类型
             */
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            String [] argv = {};
            String routingKey = getRouting(argv);
            String message = getMessage(argv);

            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
            logger.debug(" [x] Sent '" + message + "'");
            logger.info("发送成功");
        }catch(Exception e){
            logger.error("消息发送失败:{}",e);
        }
    }

    private static String getRouting(String[] strings) {
        if (strings.length < 1)
            return "anonymous.info";
        return strings[0];
    }

    private static String getMessage(String[] strings) {
        if (strings.length < 2)
            return "Hello World!";
        return joinStrings(strings, " ", 1);
    }

    private static String joinStrings(String[] strings, String delimiter, int startIndex) {
        int length = strings.length;
        if (length == 0) return "";
        if (length < startIndex) return "";
        StringBuilder words = new StringBuilder(strings[startIndex]);
        for (int i = startIndex + 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}
