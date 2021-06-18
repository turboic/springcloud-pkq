package com.turboic.cloud.consume;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author taiji
 */
@Component
@EnableBinding(KafkaInputChannel.class)
@Transactional(rollbackFor = Exception.class)
public class KafkaConsumeService {
    AtomicInteger atomicInteger = new AtomicInteger(0);

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumeService.class);

    private final KafkaInputChannel kafkaInputChannel;

    public KafkaConsumeService(KafkaInputChannel kafkaInputChannel) {
        this.kafkaInputChannel = kafkaInputChannel;
    }

    //org.springframework.messaging.MessageHandlingException: Missing header 'kafka_acknowledgment' for method parameter type [interface org.springframework.kafka.support.Acknowledgment]
    /***
     * kafka消息接收者
     * @param message
     */
    @StreamListener(KafkaInputChannel.KAFKA_CONSUME)
    public void receiveMessageForConfirm(Message<String> message) {
        String payload = message.getPayload();
        logger.info("重试次数:{}",atomicInteger.decrementAndGet());
        MessageHeaders headers = message.getHeaders();
        Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
        String topic = (String) headers.get("kafka_receivedTopic");
        logger.info("topic:{}",topic);

        long offset = (long) headers.get("kafka_offset");
        logger.info("offset:{}",offset);

        int partition = (int) headers.get("kafka_receivedPartitionId");
        logger.info("partition:{}",partition);

        String kafkaMsg = message.getPayload();
        logger.info("kafkaMsg:{}",kafkaMsg);

        logger.info("kafka消费者接收消息内容1:{}",payload);
        if(!StringUtils.isEmpty(payload)){
            logger.info("kafka消费端手动提交确认");
            if(acknowledgment != null){
                acknowledgment.acknowledge();
            }
            logger.info("消息确认接收成功:{}",payload);
        }
        else{
            logger.info("消息未确认接收:{}",payload);
        }

    }

    /*@StreamListener(KafkaInputChannel.KAFKA_CONSUME)
    public void receiveMessage(Message<String> message) {
        String payload = message.getPayload();
        Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
        logger.info("kafka消费者接收消息内容2:{}",payload);
        if (acknowledgment != null) {
            logger.info("kafka业务代码完成，手动提交");
            System.out.println("Acknowledgment provided");
            acknowledgment.acknowledge();
        }
    }*/
}
