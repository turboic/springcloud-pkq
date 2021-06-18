package com.turboic.cloud.mq;

import com.turboic.cloud.utils.FastJsonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.io.UnsupportedEncodingException;

@Component
@RocketMQMessageListener(topic = MqConstant.TRANSACTION_DESTINATION, consumerGroup = MqConstant.TRANSACTION_CONSUMER_GROUP,
        consumeMode = ConsumeMode.CONCURRENTLY,selectorExpression="*")
public class RocketmqConsumerConfig2 implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    private static final Logger log = LoggerFactory.getLogger(RocketmqConsumerConfig2.class);
    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        log.info("执行prepareStart方法");
        defaultMQPushConsumer.registerMessageListener((MessageListenerConcurrently)(messageExt, context) ->{
            try {
                log.info("【获取消息】");
                if (!CollectionUtils.isEmpty(messageExt)) {
                    messageExt.forEach(ext->{
                        Integer con = ext.getReconsumeTimes();
                        log.info("【消费消息】 次数：{}, ext ：{}", con, ext);
                        log.info("messageExt内容={}", FastJsonUtils.objectToJson(messageExt));
                    });
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } catch (Exception e){
                e.printStackTrace();
                log.error("【消费消息失败】，message：{}", e.getMessage());
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        });
    }

    /***
     * 与上面的方法互斥的，上面会执行，这个不会执行
     * @param messageExt
     */

    @Override
    public void onMessage(MessageExt messageExt) {
        log.info(messageExt.getClass().getName().toString());
       String msg;
        try {
            msg = new String(messageExt.getBody(),"utf-8");
            log.info("onMessage = {}, msgId={}", msg, messageExt.getMsgId());
        } catch (UnsupportedEncodingException e) {
            log.error("消费方法出现异常={}", e.getMessage());
        }
    }


}