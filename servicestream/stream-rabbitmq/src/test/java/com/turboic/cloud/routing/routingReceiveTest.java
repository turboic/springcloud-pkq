package com.turboic.cloud.routing;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试rabbitmq routing 模式
 * 只能使用main方法测试监听收消息
 *  routing
 */
public class routingReceiveTest
{
    private static final String EXCHANGE_NAME = "direct_logs";
    private static final Logger logger = LoggerFactory.getLogger(routingReceiveTest.class);
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
            //声明交换机名称及类型为direct
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            String queueName = channel.queueDeclare().getQueue();

            String [] argv = {"info","warning","error"};

            if (argv.length < 1) {
                System.err.println("Usage: ReceiveLogsDirect [info] [warning] [error]");
                System.exit(1);
            }

            for (String severity : argv) {
                channel.queueBind(queueName, EXCHANGE_NAME, severity);
            }
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" +
                        delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
                logger.info("routing类型工作模式消息接收成功");
            };
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });

        }catch(Exception e) {
            logger.error("消息接收失败:{}",e);
        }
    }
}
