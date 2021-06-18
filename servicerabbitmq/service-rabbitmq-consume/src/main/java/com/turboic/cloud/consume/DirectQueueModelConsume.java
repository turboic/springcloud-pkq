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
public class DirectQueueModelConsume {

    private static final Logger logger = LoggerFactory.getLogger(DirectQueueModelConsume.class);

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = Constant.DIRECT_QUEUE_MODEL_NAME, durable = "true"),
            exchange = @Exchange(
                    value = Constant.DIRECT_EXCHANGE_MODEL_NAME,
                    ignoreDeclarationExceptions = "true"
            ),
            key = {"direct"}
    ))
    public void directListener1(String msg) {
        logger.info("路由模式1 接收到消息：" + msg);
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = Constant.DIRECT_QUEUE_MODEL_NAME2, durable = "true"),
            exchange = @Exchange(
                    value = Constant.DIRECT_EXCHANGE_MODEL_NAME,
                    ignoreDeclarationExceptions = "true"
            ),
            key = {"direct-test"}
    ))
    public void directListener2(String msg) {
        logger.info("路由模式2 接收到消息：" + msg);
    }
}