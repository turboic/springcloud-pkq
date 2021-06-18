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
public class DirectQueueModelProducer {
    private static final Logger logger = LoggerFactory.getLogger(DirectQueueModelProducer.class);
    private final AmqpTemplate amqpTemplate;

    public DirectQueueModelProducer(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    /**
     *
     * @param message
     * @return
     */
    public String producer(String message,int length){
        if(length < 0 || length == 0){
            length = 10;
        }
        try{
            for (int i = 0; i < length; i++) {
                /** Direct交换机模式名称 */
                amqpTemplate.convertAndSend(Constant.DIRECT_EXCHANGE_MODEL_NAME, "direct", message + i);
            }
            Thread.sleep(1000*(length/2));
            return "Direct队列模式发送消息成功";
        }catch (Exception e){
            logger.error("消息发送异常:{}",e);
            return "Direct队列模式发送消息失败";
        }
    }
}
