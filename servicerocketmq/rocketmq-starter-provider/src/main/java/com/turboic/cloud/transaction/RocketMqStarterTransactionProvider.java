package com.turboic.cloud.transaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import java.util.UUID;

/**
 * rocketmq starter 消息生产者使用事务功能
 * @author taiji
 */
@Component
@Slf4j
public class RocketMqStarterTransactionProvider {

    @Autowired
    @Qualifier(value = "rocketMQTemplate")
    private RocketMQTemplate rocketMQTemplate;
    public TransactionSendResult transaction(String destination,String msg){
        log.info("主题destination:{},msg:{}",destination,msg);
        Message<?> message = MessageBuilder.withPayload(msg)
                .setHeader(MessageConst.PROPERTY_KEYS, System.currentTimeMillis())
                .setHeader("TAGS", "191018")
                .setHeader(MessageConst.PROPERTY_BUYER_ID, UUID.randomUUID().toString())
                .setHeader("MQ", "hello-world")
                .setHeader(MessageConst.PROPERTY_MESSAGE_REPLY_TO_CLIENT,true)
                .build();
        TransactionSendResult transactionSendResult = this.rocketMQTemplate.sendMessageInTransaction(destination,message,null);
        log.info("rocketmq事务发送消息完成");
        if(transactionSendResult != null){
            MessageQueue messageQueue = transactionSendResult.getMessageQueue();
            log.info("QueueId:{},BrokerName:{},Topic:{}",messageQueue.getQueueId(),messageQueue.getBrokerName(),messageQueue.getTopic());
            log.info("MsgId:{},transactionId:{}", transactionSendResult.getMsgId(),transactionSendResult.getTransactionId());
            SendStatus sendStatus = transactionSendResult.getSendStatus();
            log.info("sendStatusName:{},ordinal:{}", sendStatus.name(),sendStatus.ordinal());
        }
        return transactionSendResult;
    }
}
