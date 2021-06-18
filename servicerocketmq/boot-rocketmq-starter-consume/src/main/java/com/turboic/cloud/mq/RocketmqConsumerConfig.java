package com.turboic.cloud.mq;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Component
@RocketMQMessageListener(topic="liebe",consumerGroup = "CONSUMER-GROUP-1",
        consumeMode = ConsumeMode.CONCURRENTLY,selectorExpression="*")
public class RocketmqConsumerConfig implements RocketMQListener {
    private static final Logger log = LoggerFactory.getLogger(RocketmqConsumerConfig.class);

    @Override
    public void onMessage(Object o) {
        log.info("onMessage方法被执行={}",o);
        log.info("参数类型={}",o.getClass().getName().toString());
    }
}
