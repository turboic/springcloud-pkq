package com.turboic.cloud;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

/**
 * cloud-stream-kafka-consume服务
 *
 * @author liebe
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
public class KafkaConsumeApplication
{
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumeApplication.class);
    public static void main( String[] args )
    {
        System.out.println( "Hello kafka-consume!" );
        logger.info("cloud-stream-kafka-consume 微服务启动成功");
        new SpringApplicationBuilder(KafkaConsumeApplication.class).run(args);
    }
}
