package com.turboic.cloud.config;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义kafka消费者模板类
 * @author liebe
 */
@Configuration
public class KafkaConsumeConfig {
    /***
     * 创建kafka的普通消费者
     */
    @Bean
    public KafkaConsumer<String, String> kafkaConsumer() {
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(consumeConfigs());
        kafkaConsumer.subscribe(Arrays.asList(Constant.KAFKA_DEFAULT_TOPIC),new CusConsumerRebalanceListener());
        return kafkaConsumer;
    }

    public Map<String, Object> consumeConfigs() {
        Map properties = new HashMap();
        // 设置反序列化key参数信息
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // 设置反序列化value参数信息
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // 设置服务器列表信息，必填参数，该参数和生产者相同，，制定链接kafka集群所需的broker地址清单，可以设置一个或者多个
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Constant.KAFKA_BROKER_SERVER);

        // 设置消费者组信息，消费者隶属的消费组，默认为空，如果设置为空，则会抛出异常，这个参数要设置成具有一定业务含义的名称
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, Constant.KAFKA_CONSUME_GROUP);

        //自动偏移量重置配置
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"latest");
        return properties;
    }
}
