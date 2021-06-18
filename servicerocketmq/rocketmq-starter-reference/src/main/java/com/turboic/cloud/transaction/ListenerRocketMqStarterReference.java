package com.turboic.cloud.transaction;
import com.turboic.cloud.starter.RocketConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.io.UnsupportedEncodingException;
/**
 * @author liebe
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = RocketConst.destination_transaction, consumerGroup = RocketConst.consumerGroup_transaction)
public class ListenerRocketMqStarterReference implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        defaultMQPushConsumer.registerMessageListener((MessageListenerConcurrently)(messageExts, context) ->{
            try {
                log.info("【获取消息】");
                if (!CollectionUtils.isEmpty(messageExts)) {
                    messageExts.forEach(ext->{
                        Integer con = ext.getReconsumeTimes();
                        log.info("获取消息用于消费端业务逻辑处理");
                        log.info("【消费消息】 次数：{}, ext ：{}", con, ext);
                        try {
                            String msg = new String(ext.getBody(),"utf-8");
                            log.error("消费端接收的消息内容是:{}",msg);
                        } catch (UnsupportedEncodingException e) {
                            log.info("解析消息异常UnsupportedEncodingException:{}",e);
                        }
                    });
                }
                log.info("消息在消费端消费处理成功");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } catch (Exception e){
                log.error("消费消息出现异常，Exception：{}", e.getMessage());
                log.info("出现异常，后续重新执行消费");
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        });
    }

    @Override
    public void onMessage(MessageExt messageExt) {
        String msg;
        try {
            msg = new String(messageExt.getBody(),"utf-8");
            log.info("ListenerRocketMqStarterReference消费端接收的消息内容 >>> {}, 消息ID-->msgId:{}", msg, messageExt.getMsgId());
            log.info("获取消息用于消费端业务逻辑处理----2");
        } catch (UnsupportedEncodingException e) {
            log.error("消费异常：{}", e.getMessage());
        }
    }
}