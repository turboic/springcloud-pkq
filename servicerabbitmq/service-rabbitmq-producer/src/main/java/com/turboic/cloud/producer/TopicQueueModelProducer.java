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
public class TopicQueueModelProducer {
    private static final Logger logger = LoggerFactory.getLogger(TopicQueueModelProducer.class);
    private final AmqpTemplate amqpTemplate;

    public TopicQueueModelProducer(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    /**
     *
     * @return
     */
    public String producer(){
        try{
            amqpTemplate.convertAndSend(Constant.TOPIC_QUEUE_MODEL_NAME, "kafka.producer", "kafka生产者");
            amqpTemplate.convertAndSend(Constant.TOPIC_QUEUE_MODEL_NAME, "kafka.consume", "kafka消费者");
            amqpTemplate.convertAndSend(Constant.TOPIC_QUEUE_MODEL_NAME, "rabbitmq.producer", "rabbitmq生产者");
            amqpTemplate.convertAndSend(Constant.TOPIC_QUEUE_MODEL_NAME, "rabbitmq.consume", "rabbitmq消费者");
            Thread.sleep(1000*3);
            return "Topic队列模式发送消息成功";
        }catch (Exception e){
            logger.error("消息发送异常:{}",e);
            return "Topic队列模式发送消息失败";
        }
    }
}
