package com.turboic.cloud.provider;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;

/**
 * 定义kafka消息发送通道接口
 * @author liebe
 */
public interface KafkaSourceChannel extends Source {
    String KAFKA_PROVIDER = "kafkaProvider";
    /***
     * kafka发送消息的通道
     * @return
     */
    @Output(value = KAFKA_PROVIDER)
    MessageChannel kafkaProvider();
}