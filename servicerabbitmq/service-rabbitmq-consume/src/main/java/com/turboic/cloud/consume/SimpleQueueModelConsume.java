package com.turboic.cloud.consume;

import com.turboic.cloud.common.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author liebe
 */
@Component
public class SimpleQueueModelConsume {
    private static final Logger logger = LoggerFactory.getLogger(SimpleQueueModelConsume.class);

    @RabbitListener(queuesToDeclare = @Queue(Constant.SIMPLE_QUEUE_MODEL_NAME))
    public void rabbitListener(String message) {
        logger.info("SimpleQueueModelConsume简单队列模式接收消息:{}",message);
    }
}