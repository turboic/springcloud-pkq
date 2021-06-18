package com.turboic.cloud.util;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public class CustomRunnable implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(CustomRunnable.class);
    KafkaConsumer<String, String> kafkaConsumer;
    private RequestWeather requestWeather;
    private String name;
    public CustomRunnable(String name,KafkaConsumer<String, String> kafkaConsumer,RequestWeather requestWeather){
        this.name = name;
        this.kafkaConsumer = kafkaConsumer;
        this.requestWeather = requestWeather;
    }
    @Override
    public void run() {        
        while(true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(kafkaConsumer == null){
                logger.error("kafkaConsumer实例为空,初始化失败");
                break;
            }
            if(requestWeather == null){
                requestWeather = new RequestWeather(new RestTemplate());
            }
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(1000));
            boolean flag = false;
            if (consumerRecords != null && !consumerRecords.isEmpty() && consumerRecords.count() > 0) {
                Optional<ConsumerRecords<String, String>> optionalConsumerRecords = Optional.ofNullable(consumerRecords);
                if (optionalConsumerRecords.isPresent()) {
                    for (ConsumerRecord<String, String> record : consumerRecords) {
                        logger.error(this.name +"key:{},value:{}", record.key(), record.value());
                        if(!StringUtils.isEmpty(record.value())){
                            requestWeather.weather(record.value());
                        }
                        String topic = record.topic();
                        int partition = record.partition();
                        long offset = record.offset();
                        logger.error("topic:{},partition:{},offset:{}", topic, partition,offset);
                    }
                    Iterator<ConsumerRecord<String, String>> iterator = optionalConsumerRecords.get().iterator();
                    while (iterator.hasNext()) {
                        ConsumerRecord<String, String> consumerRecord = iterator.next();
                        logger.error(FastJsonUtils.objectToJson(consumerRecord));
                    }
                    Set<TopicPartition> topicPartitions = optionalConsumerRecords.get().partitions();
                    if (!CollectionUtils.isEmpty(topicPartitions)) {
                        for(TopicPartition topicPartition :topicPartitions){
                            logger.error(this.name + "循环打印TopicPartition内容:{}", FastJsonUtils.objectToJson(topicPartition));
                        }
                    }
                    flag = true;
                }
                if (flag) {
                    kafkaConsumer.commitAsync((map, e) -> logger.info(this.name + "kafka消费完成异步提交:{},Exception:{}", FastJsonUtils.objectToJson(optionalConsumerRecords),
                            FastJsonUtils.objectToJson(e)));
                    kafkaConsumer.commitAsync((map, e) -> {
                        if(map != null && map.size() > 0){
                            map.forEach((k,v)->{
                                logger.info("kafka-commitAsync-onComplete消费完成异步提交,TopicPartition:{},OffsetAndMetadata:{}",
                                        FastJsonUtils.objectToJson(k),
                                        FastJsonUtils.objectToJson(v));
                            });
                        }
                        if(e != null && !StringUtils.isEmpty(e.getMessage())){
                            logger.info("kafka-commitAsync-onComplete消费完成异步提交,Exception:{}",e);
                        }
                    });
                }
            }
        }
    }
}
