package com.turboic.cloud.config;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;
/**
 * 自定义生产者模板类
 * 配置信息可考虑独立到properties文件或者yml文件中去
 * 暂时赋值固定
 * @author liebe
 */
@Configuration
@EnableKafka
@EnableTransactionManagement
public class KafkaProducerConfig {

    /**
     * kafka事务管理
     * @param producerFactory
     * @return
     */
    @Bean
    public KafkaTransactionManager<String, String> KafkaTransactionManager(
            ProducerFactory<String, String> producerFactory) {
        return new KafkaTransactionManager<>(producerFactory);
    }

    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>(100);
        //kafka server服务器地址
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        //客户端dns寻找
        //String must be one of: default, use_all_dns_ips, resolve_canonical_bootstrap_servers_only
        props.put(ProducerConfig.CLIENT_DNS_LOOKUP_CONFIG, "default");
        //元数据最大配置生存周期
        props.put(ProducerConfig.METADATA_MAX_AGE_CONFIG, 3000000);
        //批量提交
        props.put(ProducerConfig.BATCH_SIZE_CONFIG,16384000);
        //应答确认
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.LINGER_MS_CONFIG,3000);
        //请求超时
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG,6000);
        //传递超时
        //delivery.timeout.ms should be equal to or larger than linger.ms + request.timeout.ms
        props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG,15000);
        //客户端Id
        props.put(ProducerConfig.CLIENT_ID_CONFIG,"kafka-client-liebe");
        //发送字节缓存
        props.put(ProducerConfig.SEND_BUFFER_CONFIG,131072);
        //接收字节缓存
        props.put(ProducerConfig.RECEIVE_BUFFER_CONFIG,131072);
        //最大请求数量
        props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG,1048576);
        //重连等待时间
        props.put(ProducerConfig.RECONNECT_BACKOFF_MS_CONFIG,3000);
        //重连等待最大时间
        props.put(ProducerConfig.RECONNECT_BACKOFF_MAX_MS_CONFIG, 30000);
        //内存配置
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 335544320);

        //重试等待
        props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG,3000);
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip");
        props.put(ProducerConfig.METRICS_SAMPLE_WINDOW_MS_CONFIG,3000);
        props.put(ProducerConfig.METRICS_NUM_SAMPLES_CONFIG,"1");
        //String must be one of: INFO, DEBUG
        props.put(ProducerConfig.METRICS_RECORDING_LEVEL_CONFIG,"DEBUG");
        //Must set max.in.flight.requests.per.connection to at most 5 to use the idempotent producer
        //至多设置5
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION,5);
        //配置重试次数
        props.put(ProducerConfig.RETRIES_CONFIG,30);
        //键序列化
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,org.apache.kafka.common.serialization.StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,org.apache.kafka.common.serialization.StringSerializer.class);
        props.put(ProducerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG,3000);

        //props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, org.apache.kafka.clients.producer.Partitioner.class);

        //开启幂等性
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);

        //事务超时时间
        props.put(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG, 60000);
        //批量提交
        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG,"tx-");

        //props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, org.apache.kafka.clients.producer.ProducerInterceptor.class);
        
        return props;
    }

    /**
     * kafka生产者工厂
     * @return
     */
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        DefaultKafkaProducerFactory producerFactory = new DefaultKafkaProducerFactory(producerConfigs());
        producerFactory.setPhysicalCloseTimeout(60000);
        producerFactory.setProducerPerThread(true);
        producerFactory.setProducerPerConsumerPartition(true);
        producerFactory.transactionCapable();
        return producerFactory;
    }

    /**
     * 生产者模板
     * @return
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        //关联生产者工厂，自动刷新
        KafkaTemplate template = new KafkaTemplate<>(producerFactory(),true);
        return template;
    }
}
