package com.turboic.cloud.service.impl;
import com.turboic.cloud.config.Constant;
import com.turboic.cloud.service.KafkaInstanceService;
import com.turboic.cloud.util.FastJsonUtils;
import kafka.common.KafkaException;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.OutOfOrderSequenceException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.metrics.KafkaMetricsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaInstanceServiceImpl implements KafkaInstanceService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaInstanceServiceImpl.class);

    private final Producer producer;

    public KafkaInstanceServiceImpl(Producer producer) {
        this.producer = producer;
    }

    @Override
    public void send(String msg) {
        ProducerRecord<String,String> producerRecord = new ProducerRecord<>(Constant.KAFKA_DEFAULT_TOPIC,System.currentTimeMillis()+"",msg);
        try{
            producer.beginTransaction();
            producer.send(producerRecord, (recordMetadata, e) -> {
                logger.info("kafka生产者发送消息完成RecordMetadata:{},Exception:{}",
                        FastJsonUtils.objectToJson(recordMetadata), FastJsonUtils.objectToJson(e));
                logger.info(String.format("offset:%s,partition:%s",recordMetadata.offset(),
                        recordMetadata.partition()));
            });
            producer.flush();
            //偏移量需要消费端提交
            /*
            Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<TopicPartition, OffsetAndMetadata>() {
                {
                    put(new TopicPartition(Constant.KAFKA_DEFAULT_TOPIC, 0), new OffsetAndMetadata(42L, null));
                }
            };
            producer.sendOffsetsToTransaction(offsets,Constant.KAFKA_CONSUME_GROUP);*/
            producer.commitTransaction();
        }catch(ProducerFencedException | OutOfOrderSequenceException | AuthorizationException e){
            producer.close();
            logger.error("kafka生产者发送消息出现异常:{}",e);
            throw e;
        }
        catch (KafkaException e) {
            producer.abortTransaction();
            logger.error("kafka生产者发送消息出现异常:{}",e);
            throw e;
        }
    }
}
