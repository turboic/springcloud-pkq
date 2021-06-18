package com.turboic.cloud;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liebe
 * 用于配置AmqpTemplate模板Bean
 */
@Configuration
public class DefaultRabbitMqConfiguration {

    /**
     * 默认队列的名称
     */
    private final String defaultQueueName = "test";
    private final String setRoutingKey = "liebe";


    /**
     * 创建连接工厂
     */


   @Bean
    public ConnectionFactory connectionFactory() {
       CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
       cachingConnectionFactory.setAddresses("10.10.10.10");
       cachingConnectionFactory.setUsername("zhxl");
       cachingConnectionFactory.setPassword("zhxl");
       cachingConnectionFactory.setPort(5672);
       cachingConnectionFactory.setVirtualHost("/");
       cachingConnectionFactory.setConnectionTimeout(60000);
       return cachingConnectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setRoutingKey(this.setRoutingKey);
        template.setEncoding("utf-8");
        template.setReplyTimeout(60000);
        template.setBeanName("rabbitTemplate");
        template.setChannelTransacted(true);
        template.setUseTemporaryReplyQueues(true);
        template.setDefaultReceiveQueue(this.defaultQueueName);
        return template;
    }

    @Bean
    public Queue helloWorldQueue() {
        return new Queue(this.defaultQueueName);
    }
}