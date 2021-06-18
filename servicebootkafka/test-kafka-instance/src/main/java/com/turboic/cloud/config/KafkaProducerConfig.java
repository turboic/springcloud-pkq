package com.turboic.cloud.config;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Map;
import java.util.Properties;

/**
 * 自定义生产者模板类
 * @author liebe
 */
@Configuration
public class KafkaProducerConfig {


    /***
     * 创建kafka生产者的实例对象
     * @return
     */
    @Bean
    public Producer<String, String> producer() {
        Producer<String, String> producer = new KafkaProducer<>(producerConfigs());
        producer.initTransactions();
        return producer;
    }

    /**
     * 简单的配置
     * @return
     */
    public Map<String, Object> producerConfigs() {
        Map properties = new Properties();
        properties.put("bootstrap.servers", Constant.KAFKA_BROKER_SERVER);
        //需要所有的确认
        properties.put(ProducerConfig.ACKS_CONFIG,"all");
        properties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG,60000);
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,true);
        //默认重试次数
        properties.put("retries", 3);
        //批量发包数量
        properties.put("batch.size", 16384);
        //上面比如我们设置batch size为16KB,但是比如有的时刻消息比较少,过了很久,比如5min也没有凑够16KB,
        // 这样延时就很大,所以需要一个参数. 再设置一个时间,到了这个时间,即使数据没达到16KB,也将这个批次发送出去.
        // 比如设置5ms,就是到了5ms,大小没到16KB,也会发出去
        properties.put("linger.ms", 2);
        properties.put("buffer.memory", 33554432);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG,Constant.TRANSACTIONAL_ID_CONFIG);
        properties.put(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG,60000);
        return properties;
    }
}
