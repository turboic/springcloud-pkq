package com.turboic.cloud.delay;
import com.rabbitmq.client.Channel;
import com.turboic.cloud.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Date;

/***
 *
 * 消息接收接口
 * 绑定输入的通道
 * @author liebe
 */
@EnableBinding(CustomInput.class)
public class ReceiveMessageImpl implements ReceiveMessage{
    private final static Logger logger = LoggerFactory.getLogger(ReceiveMessageImpl.class);

    /***
     * 主动抛出异常，被注解@StreamListener监听的方法默认重复3次
     * 需要在application.yml中开启失败后重新加入队列
     *
     * 进而使用死信队列的机制
     */

    /**
     * 消息接收的接口
     * 监控消息通道
     * @param message
     */
    @Override
    @StreamListener(CustomInput.MSG_CONSUME)
    public void receiveMsg(Message<String> message) {
        String s = message.getPayload();
        logger.info(DateUtils.dateConvertString(new Date()));
        logger.info(s);
        throw new RuntimeException("Message consumer failed!");

    }

    /***
     * 手动确认消息已经接收成功
     * @param message
     * @param channel
     * @param deliveryTag
     */
    //@StreamListener(CustomInput.MSG_CONSUME)
    public void receiveMessageForConfirm(Message<String> message,
                                         @Header(AmqpHeaders.CHANNEL) Channel channel,
                                         @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) {
        String payload = message.getPayload();
        if(!StringUtils.isEmpty(payload)){
           try {
                /***手动确认提交消息***/
                channel.basicAck(deliveryTag, false);
            } catch (IOException e) {
                logger.error("手动确认提交消息失败:{}",e);
                e.printStackTrace();
            }
        }
        logger.info(DateUtils.dateConvertString(new Date()));
        logger.info("消息确认接收成功:{}",payload);
    }
}
