package com.turboic.cloud.producer;
import com.turboic.cloud.common.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

/**
 * @author liebe
 */
@Component
public class SimpleQueueModelProducer {
    private static final Logger logger = LoggerFactory.getLogger(SimpleQueueModelProducer.class);
    private final AmqpTemplate amqpTemplate;

    public SimpleQueueModelProducer(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    /**
     *
     * @param message
     * @return
     */
    public String producer(String message){
        try{
            amqpTemplate.convertAndSend(Constant.SIMPLE_QUEUE_MODEL_NAME,message);
            return "简单队列模式发送消息成功";
        }catch (Exception e){
            logger.error("消息发送异常:{}",e);
            return "简单队列模式发送消息失败";
        }
    }
}
