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
public class FanoutQueueModelProducer {
    private static final Logger logger = LoggerFactory.getLogger(FanoutQueueModelProducer.class);
    private final AmqpTemplate amqpTemplate;

    public FanoutQueueModelProducer(AmqpTemplate amqpTemplate) {
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
                amqpTemplate.convertAndSend(Constant.FANOUT_EXCHANGE_MODEL_NAME, "", message + i);
            }
            Thread.sleep(1000*5);
            return "Fanout队列模式发送消息成功";
        }catch (Exception e){
            logger.error("消息发送异常:{}",e);
            return "Fanout队列模式发送消息失败";
        }
    }
}
