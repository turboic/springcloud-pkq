/*
package com.turboic.cloud.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

*/
/**
 * @author liebe
 * 消息生产者
 *//*

@EnableBinding(Source.class)
@Component
public class RabbitmqPublisher{

    private final MessageChannel messageChannel;

    @Autowired
    public RabbitmqPublisher(MessageChannel messageChannel) {
        this.messageChannel = messageChannel;
    }

    */
/**
     * 发送消息的方法
     * @param msg
     * @return
     *//*

    public boolean send(String msg) {
        return messageChannel.send(MessageBuilder.withPayload(msg).build());
    }
}*/
