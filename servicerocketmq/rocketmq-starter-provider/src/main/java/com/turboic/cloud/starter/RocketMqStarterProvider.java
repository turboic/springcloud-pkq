package com.turboic.cloud.starter;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * rocketmq starter 消息生产者
 * @author taiji
 */
@Component
@Slf4j
public class RocketMqStarterProvider {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    public String send(String destination,String msg){
        log.info("destination:{},msg:{}",destination,msg);
        this.rocketMQTemplate.convertAndSend(destination,msg);
        log.info("RocketMqStarterProvider.convertAndSend消息发送成功");
        Message<?> message = MessageBuilder.withPayload(msg)
                .setHeader(MessageConst.PROPERTY_KEYS, System.currentTimeMillis())
                .setHeader("TAGS", "191018")
                .setHeader(MessageConst.PROPERTY_BUYER_ID, UUID.randomUUID().toString())
                .setHeader("MQ", "hello-world")
                .setHeader(MessageConst.PROPERTY_MESSAGE_REPLY_TO_CLIENT,true)
                .build();
        this.rocketMQTemplate.send(destination,message);
        log.info("RocketMqStarterProvider.send消息发送成功");
        return "rocketmq消息发送成功";
    }
}
