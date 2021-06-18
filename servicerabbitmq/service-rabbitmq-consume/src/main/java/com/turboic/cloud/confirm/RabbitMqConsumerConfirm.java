package com.turboic.cloud.confirm;
import com.rabbitmq.client.Channel;
import com.turboic.cloud.common.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @author libe
 */
@Component
public class RabbitMqConsumerConfirm {
    private static Logger log = LoggerFactory.getLogger(RabbitMqConsumerConfirm.class);

    @RabbitHandler
    @RabbitListener(queues = "queue.hello")
    public void consumeConfirm(Message message, Channel channel) throws IOException {
        log.info("接收消息内容: " + new String(message.getBody())+"《线程名：》"+Thread.currentThread().getName()+"《线程id:》"+Thread.currentThread().getId());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.info("消费端确认接收完成");
    }


}