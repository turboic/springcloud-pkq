package com.turboic.cloud.mq;
import com.turboic.cloud.utils.FastJsonUtils;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import java.util.UUID;
@Component
public class RocketmqProducerConfig {
    private static final Logger log = LoggerFactory.getLogger(RocketmqProducerConfig.class);
    private final RocketMQTemplate rocketMQTemplate;
    private static final String DEFAULT_TOPIC = "20210530";
    private static final String DEFAULT_TAGS = "消息中间件";
    private static final boolean WAIT_STORE_MSG_OK = true;
    private static final String destination = "liebe";
    private static final long timeout = 3000l;
    private static final int delayLevel = 500;
    //================================================================================================
    private static final String TRANSACTION_DESTINATION = "douyin";
    private static final String TRANSACTION_TOPIC = "xi_yan";



    public RocketmqProducerConfig(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    public String send(String msg){
        StringBuffer stringBuilder = new StringBuffer();
        try{
            log.info("消息原内容是={}",msg);
            Message message = MessageBuilder.withPayload(msg).build();
            SendResult sendResult = rocketMQTemplate.syncSend(destination,message,timeout,delayLevel);
            if (sendResult == null){
                return "sendResult为空";
            }
            MessageQueue messageQueue = sendResult.getMessageQueue();
            if (messageQueue != null) {
                log.info("同步请求返回结果，消息队列内容={}",FastJsonUtils.objectToJson(messageQueue));
            }
            SendStatus sendStatus = sendResult.getSendStatus();
            if (sendStatus != null) {
                log.info("同步请求返回结果，发送状态内容={}",FastJsonUtils.objectToJson(sendStatus));
            }
            log.info("同步请求返回结果,消息Id={},区域Id={},偏移量消息Id={},队列偏移量={}," +
                            "事务Id={},是否开始trace={}", sendResult.getMsgId(),sendResult.getRegionId(),
                    sendResult.getOffsetMsgId(),sendResult.getQueueOffset(),sendResult.getTransactionId(),
                    sendResult.isTraceOn());

            stringBuilder.append("消息ID:"+sendResult.getMsgId()+",");
            stringBuilder.append("区域ID:"+sendResult.getRegionId()+",");
            stringBuilder.append("事务ID:"+sendResult.getTransactionId()+",");
            stringBuilder.append("偏移量消息ID:"+sendResult.getOffsetMsgId()+",");
            stringBuilder.append("队列偏移量:"+sendResult.getQueueOffset()+",");
            stringBuilder.append("是否开启trace:"+sendResult.isTraceOn()+".");
        }catch (Exception e){
            log.error("出现异常={}",e);
            return "消息发送失败:"+e.getMessage();
        }
        return "消息发送成功:"+stringBuilder.toString();
    }

    public String sendOneWay(String msg){
        try{
            rocketMQTemplate.sendOneWay(destination,msg);
        }catch (Exception e){
            log.error("出现异常={}",e);
            return "sendOneWay操作失败"+e.getMessage();
        }
        return "sendOneWay操作成功";
    }


    /***
     * 事务发送
     * @param msg
     * @return
     *
     * 没有主题可以自动创建的
     * transactionSendResult={"localTransactionState":"UNKNOW","messageQueue":{"brokerName":"10.10.10.7","queueId":2,
     * "topic":"douyin"},"msgId":"7F0000012BFC18B4AAC29A8274110000","queueOffset":68,"sendStatus":"SEND_OK","traceOn":true}
     *
     * 流程：org.apache.rocketmq.client.exception.MQClientException: No route info of this topic: douyin
     * TRANSACTION_DESTINATION表示主题的名称
     * 第二个参数是封装的message
     * 第三个参数是
     */
    public String sendMessageInTransaction(String msg){
        String transactionId = UUID.randomUUID().toString().replace("-", "");
        log.info("【发送半消息】transactionId={}", transactionId);
        TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction(TRANSACTION_DESTINATION,
                MessageBuilder.withPayload(msg).setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId).build(),TRANSACTION_TOPIC);
        if (transactionSendResult != null) {
            log.error("transactionSendResult={}",FastJsonUtils.objectToJson(transactionSendResult));
        }
        return FastJsonUtils.objectToJson(transactionSendResult);
    }
}
