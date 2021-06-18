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
public class WorkQueueModelConsume {
    private static final Logger logger = LoggerFactory.getLogger(WorkQueueModelConsume.class);
    @RabbitListener(queuesToDeclare = @Queue(Constant.WORK_QUEUE_MODEL_NAME))
    public void listen(String message) {
        logger.info("work模式1 接收到消息：:{}",message);
    }
    @RabbitListener(queuesToDeclare = @Queue(Constant.WORK_QUEUE_MODEL_NAME))
    public void listen2(String message) {
        logger.info("work模式2 接收到消息：:{}",message);
    }
}