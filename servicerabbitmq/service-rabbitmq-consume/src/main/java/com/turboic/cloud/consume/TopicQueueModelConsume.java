package com.turboic.cloud.consume;
import com.turboic.cloud.common.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author liebe
 */
@Component
public class TopicQueueModelConsume {

    private static final Logger logger = LoggerFactory.getLogger(TopicQueueModelConsume.class);
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = Constant.TOPIC_QUEUE_MODEL_NAME, durable = "true"),
            exchange = @Exchange(
                    value = Constant.TOPIC_EXCHANGE_MODEL_NAME,
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC
            ),
            key = {"kafka.*"}
    ))
    public void kafkaTopic(String message) {
        logger.info("topic交换机模式-->kafka.*接收消息:{}" + message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = Constant.TOPIC_QUEUE_MODEL_NAME, durable = "true"),
            exchange = @Exchange(
                    value = Constant.TOPIC_EXCHANGE_MODEL_NAME,
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC
            ),
            key = {"rabbitmq.*"}
    ))
    public void rabbitmqTopic(String message) {
        logger.info("topic交换机模式-->rabbitmq.*接收消息:{}" + message);
    }
}