package com.turboic.cloud;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * stream-rabbitmq 延时队列的处理
 * 延迟队列的实现
 * @author liebe
 */
@SpringBootApplication
public class StreamRabbitMQDelayQueuesApplication
{
    public static void main( String[] args )
    {
        System.out.println( "Hello stream rabbitmq server delay queues!" );
        new SpringApplicationBuilder(StreamRabbitMQDelayQueuesApplication.class).run(args);
    }
}
