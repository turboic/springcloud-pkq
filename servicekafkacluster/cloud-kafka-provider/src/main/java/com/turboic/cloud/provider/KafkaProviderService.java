package com.turboic.cloud.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author liebe
 */
@Component
@EnableBinding(KafkaSourceChannel.class)
@Transactional(rollbackFor = Exception.class)
public class KafkaProviderService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProviderService.class);

    private final KafkaSourceChannel kafkaSourceChannel;

    public KafkaProviderService(KafkaSourceChannel kafkaSourceChannel) {
        this.kafkaSourceChannel = kafkaSourceChannel;
    }

    /**
     * 发送消息的方法
     * @param message
     * @return
     */
    public boolean provider(String message,long time){
        if(time < 0 || time == 0){
            time = 3000;
        }
        boolean result = kafkaSourceChannel.kafkaProvider().send(MessageBuilder.withPayload(message).build(),time);
        logger.error("发送消息:{},结果:{}",message,result);
        return result;
    }
}

