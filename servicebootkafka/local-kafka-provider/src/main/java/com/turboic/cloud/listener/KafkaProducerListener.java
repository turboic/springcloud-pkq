package com.turboic.cloud.listener;
import com.turboic.cloud.KafkaProviderApplication;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

/**
 * @author liebe
 */
@Component
public class KafkaProducerListener implements ProducerListener {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProviderApplication.class);

    /**
     * 发送消息成功后调用
     */
    @Override
    public void onSuccess(String topic, Integer partition, Object key,
                          Object value, RecordMetadata recordMetadata) {
        this.onSuccess(topic, partition, key, value, recordMetadata);
        logger.info("topic:{}, partition:{}, key:{}, value:{}, recordMetadata:{}",
                topic, partition, key, value, recordMetadata);
        logger.info("onSuccess消息发送成功！");
    }

    /**
     * 发送消息错误后调用
     */
    @Override
    public void onError(String topic, Integer partition, Object key,
                        Object value, Exception exception) {
        this.onError(topic, partition, key, value, exception);
        logger.error("topic:{}, partition:{}, key:{}, value:{}, exception:{}",
                topic, partition, key, value, exception);
        logger.error("onError消息发送失败！");
    }

    /**
     * 是否开启发送监听
     *
     * @return true开启，false关闭
     */
    @Override
    public boolean isInterestedInSuccess() {
        return true;
    }

}