package com.turboic.cloud.util;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import java.time.Duration;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

@Component
public class DynamicRunnable implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(DynamicRunnable.class);
    KafkaConsumer<String, String> kafkaConsumer;
    private RequestWeather requestWeather;
    public DynamicRunnable(KafkaConsumer<String, String> kafkaConsumer, RequestWeather requestWeather){
        this.kafkaConsumer = kafkaConsumer;
        this.requestWeather = requestWeather;
    }
    @Override
    public void run() {        
        while(true) {
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(1000));
            boolean flag = false;
            if (consumerRecords != null && !consumerRecords.isEmpty() && consumerRecords.count() > 0) {
                Optional<ConsumerRecords<String, String>> optionalConsumerRecords = Optional.ofNullable(consumerRecords);
                if (optionalConsumerRecords.isPresent()) {
                    for (ConsumerRecord<String, String> record : consumerRecords) {
                        logger.error("key:{},value:{}", record.key(), record.value());
                        if(!StringUtils.isEmpty(record.value()) && record.value().length() > 0){
                            requestWeather.weather(record.value());
                        }
                        String topic = record.topic();
                        int partition = record.partition();
                        long offset = record.offset();
                        logger.error("topic:{},partition:{},offset:{}", topic, partition,offset);
                    }
                    ConsumerRecords<String, String> records = optionalConsumerRecords.get();
                    Iterator<ConsumerRecord<String, String>> iterator = records.iterator();
                    while (iterator.hasNext()) {
                        ConsumerRecord<String, String> consumerRecord = iterator.next();
                        logger.error(FastJsonUtils.objectToJson(consumerRecord));
                    }
                    Set<TopicPartition> topicPartitions = optionalConsumerRecords.get().partitions();
                    if (!CollectionUtils.isEmpty(topicPartitions)) {
                        for(TopicPartition topicPartition :topicPartitions){
                            logger.error("循环打印TopicPartition内容:{}", FastJsonUtils.objectToJson(topicPartition));
                        }
                    }
                    flag = true;
                }
                if (flag) {
                    kafkaConsumer.commitAsync((map, e) -> logger.info("kafka消费完成异步提交:{},Exception:{}", FastJsonUtils.objectToJson(optionalConsumerRecords),
                            FastJsonUtils.objectToJson(e)));
                    kafkaConsumer.commitAsync((map, e2) -> {
                        if(map != null && map.size() > 0){
                            map.forEach((k,v)->{
                                logger.info("kafka-commitAsync-onComplete消费完成异步提交,TopicPartition:{},OffsetAndMetadata:{}",
                                        FastJsonUtils.objectToJson(k),
                                        FastJsonUtils.objectToJson(v));
                            });
                        }
                    });
                }
            }
        }
    }
}
