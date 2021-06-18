package com.turboic.cloud.service;
import com.turboic.cloud.constant.KafkaConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFuture;
import java.util.UUID;
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
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class KafkaProductService {
    private final KafkaTemplate kafkaTemplate;
    @Autowired
    public KafkaProductService(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    /***
     * 异步发送
     * 消息超长，producer没有收到回调信息
     * @param message
     * @param type
     */
    public void product(String message,String type) {
        if(StringUtils.isEmpty(message)){
            message = "kafka发送的消息为空!";
        }
        String kafkaMsg = message;
        log.error("kafka product发送消息内容:{}",kafkaMsg);
        ListenableFuture listenableFuture = kafkaTemplate.send(KafkaConstant.default_topic,kafkaMsg);
        listenableFuture.addCallback(
                o -> {
                    //此处设置product设置的机制是kafka集群中所有节点都接收到消息，才算发送成功，All
                    log.info("生产者向Kafka Broker 消息发送成功,{}", o.toString());
                },
                //生产者向Kafka Broker 消息发送失败,{}Failed to send;
                // nested exception is org.apache.kafka.common.errors.TimeoutException:
                // Topic liebe not present in metadata after 60000 ms.
                throwable -> {
                    log.info("生产者向Kafka Broker 消息发送失败,{}" + throwable.getMessage());
                    log.info("生产者向Kafka Broker 消息发送失败!+1");
                    log.info("生产者向Kafka Broker 消息发送失败!+2");
                    log.info("生产者向Kafka Broker 消息发送失败!+3");
                }
        );
        listenableFuture.addCallback(s->{
            log.info("SuccessCallback:{}",s);
        },f->{
            log.info("FailureCallback:{}",f);
        });
        CompletableFuture completableFuture = listenableFuture.completable();
        try {
            Object object = completableFuture.get();
            log.info("completableFuture:{}",object);
        } catch (InterruptedException e) {
            log.info("InterruptedException:{}",e);
        } catch (ExecutionException e) {
            log.info("ExecutionException:{}",e);
        }

    }
}
