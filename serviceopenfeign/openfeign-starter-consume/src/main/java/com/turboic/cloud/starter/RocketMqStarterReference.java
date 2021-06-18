package com.turboic.cloud.starter;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author liebe
 * rocket消费监听类
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = RocketConst.destination, consumerGroup = RocketConst.consumerGroup)
public class RocketMqStarterReference implements RocketMQListener<String>
{
    @Override
    public void onMessage(String msg)
    {
        log.info("收到消息:" + msg);
    }
}
