package com.turboic.cloud.service;
import com.turboic.cloud.KafkaConsumeApplication;
import com.turboic.cloud.constant.KafkaConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author liebe
 * 开启了事务并且配置事务管理器
 * java.lang.IllegalStateException: No transaction is in process; possible solutions:
 * run the template operation within the scope of a template.executeInTransaction() operation,
 * start a transaction with @Transactional before invoking the template method,
 * run in a transaction started by a listener container when consuming a record
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class KafkaConsumeServiceListener {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumeServiceListener.class);

   /*@KafkaListener(topics = KafkaConstant.customer_topic, id = KafkaConstant.CUSTOMER_CLIENT_ID_CONFIG, containerFactory = "kafkaListenerContainerFactory")
    public void consumerMsg(List<ConsumerRecord> consumerRecords , Acknowledgment acknowledgment){
        try {
            for (ConsumerRecord record : consumerRecords) {
                logger.info(String.format("offset = %d, key = %s, value = %s%n \n", record.offset(), record.key(), record.value()));
            }
        } catch (Exception e) {
            logger.error("异常:{}",e);
        } finally {
            acknowledgment.acknowledge();
        }
    }*/

    /**
     * Caused by: org.apache.kafka.common.errors.ProducerFencedException:
     * Producer attempted an operation with an old epoch. Either there is a newer producer with the same transactionalId,
     * or the producer's transaction has been expired by the broker.
     */


    /**
     * org.apache.kafka.common.errors.FencedInstanceIdException:
     * The broker rejected this static consumer since another consumer
     * with the same group.instance.id has registered with a different member.id.
     * @param message
     * @param receivedPartitionId
     * @param receiveTopic
     * @param consumer
     * @param acknowledgment
     */
    @KafkaListener(topics = KafkaConstant.default_topic, id = KafkaConstant.CLIENT_ID_CONFIG,
            containerFactory = "kafkaListenerContainerFactory")
    public void onMessage(@Payload String message,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int receivedPartitionId,
                        @Header(KafkaHeaders.RECEIVED_TOPIC) String receiveTopic,
                        Consumer consumer,
                        Acknowledgment acknowledgment){
        logger.info("Payload:{}",message);
        logger.info("@Header(KafkaHeaders.RECEIVED_PARTITION_ID):{}",receivedPartitionId);
        logger.info("@Header(KafkaHeaders.RECEIVED_TOPIC):{}",receiveTopic);
        Random random = new Random();
        int a = random.nextInt(100);
        if(a % KafkaConstant.OUSHU ==0 ){
            acknowledgment.acknowledge();
            logger.error("执行了:{}操作,a的值为:{}","acknowledgment.acknowledge();",a);
        }
        else{
           consumer.commitAsync();
            logger.error("执行了:{}操作,a的值为:{}","consumer.commitAsync();",a);
        }
    }
}
