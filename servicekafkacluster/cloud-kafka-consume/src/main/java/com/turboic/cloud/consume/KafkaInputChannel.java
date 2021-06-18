package com.turboic.cloud.consume;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * 定义kafka消息发送通道接口
 * @author liebe
 */
public interface KafkaInputChannel extends Input {
    String KAFKA_CONSUME = "kafkaConsume";
    /***
     * kafka发送消息的通道
     * @return
     */
    @Input(value = KAFKA_CONSUME)
    SubscribableChannel kafkaConsume();
}
