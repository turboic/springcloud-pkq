package com.turboic.cloud.config;
import com.turboic.cloud.mq.RocketmqProducerConfig;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

/**
 * RocketMQTransactionListener注解必须加上，否则报错rocketMQTemplate找不到事务监听
 */
@Component
@RocketMQTransactionListener(corePoolSize=2, maximumPoolSize=4,keepAliveTime = 60000L,
        blockingQueueSize=2000,rocketMQTemplateBeanName = "rocketMQTemplate")
public class ProducerConfigTransactionListener implements RocketMQLocalTransactionListener {
    private static final Logger log = LoggerFactory.getLogger(RocketmqProducerConfig.class);
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        try{
            log.error("executeLocalTransaction ..............");
            MessageHeaders messageHeaders = message.getHeaders();
            String transactionId = (String) messageHeaders.get(RocketMQHeaders.TRANSACTION_ID);
            log.info("executeLocalTransaction方法被调用,transactionId={}",transactionId);
            Object payload = message.getPayload();
            log.info("executeLocalTransaction方法被调用,payload={}",payload.toString());
            log.info("executeLocalTransaction方法被调用,Object={}",o.getClass().getName().toString());
            return RocketMQLocalTransactionState.UNKNOWN;
        }catch (Exception e){
            log.error("执行executeLocalTransaction方法出现异常={}",e);
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        log.error("checkLocalTransaction ..............");
        MessageHeaders messageHeaders = message.getHeaders();
        String transactionId = (String) messageHeaders.get(RocketMQHeaders.TRANSACTION_ID);
        log.info("executeLocalTransaction方法被调用,transactionId={}",transactionId);
        Object payload = message.getPayload();
        log.info("executeLocalTransaction方法被调用,payload={}",payload.toString());
        return RocketMQLocalTransactionState.COMMIT;
    }
}
