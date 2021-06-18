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
public class FanoutQueueModelConsume {

    private static final Logger logger = LoggerFactory.getLogger(FanoutQueueModelConsume.class);

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = Constant.FANOUT_QUEUE_MODEL_NAME, durable = "true"),
            exchange = @Exchange(
                    value = Constant.FANOUT_EXCHANGE_MODEL_NAME,
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.FANOUT
            )
    ))
    public void fanoutListener1(String message) {
        logger.info("订阅模式1 接收到消息：" + message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = Constant.FANOUT_QUEUE_MODEL_NAME2, durable = "true"),
            exchange = @Exchange(
                    value = Constant.FANOUT_EXCHANGE_MODEL_NAME,
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.FANOUT
            )
    ))
    public void fanoutListener2(String message) {
        logger.info("订阅模式2 接收到消息：" + message);
    }
}