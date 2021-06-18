package com.turboic.cloud.workQueues;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 测试rabbitmq work queues 模式
 * Work queues
 *
 * 测试通过
 */
public class workQueuesSendTest
{
    private static final String TASK_QUEUE_NAME = "task_queue";
    private static final Logger logger = LoggerFactory.getLogger(workQueuesSendTest.class);
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
            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
            String [] argv = {"皮卡丘",".","程序猿","北京",".","rabbitmq学习SpringCloud"};
            String message = String.join(" ", argv);

            channel.basicPublish("", TASK_QUEUE_NAME,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
            logger.debug(" [x] Sent '" + message + "'");
        }catch(Exception e){
            logger.error("消息发送失败:{}",e);
        }
    }
}
