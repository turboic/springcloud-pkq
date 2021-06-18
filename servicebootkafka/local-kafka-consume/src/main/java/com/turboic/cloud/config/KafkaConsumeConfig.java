package com.turboic.cloud.config;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 消费监听配置
 * 配置信息可考虑独立到properties文件或者yml文件中去
 * 暂时赋值固定
 * @author liebe
 */
@Configuration
@EnableKafka
@EnableTransactionManagement
public class KafkaConsumeConfig {

    public Map<String, Object> consumeConfigs() {
        Map<String, Object> props = new HashMap<>(100);
        //消费者组名
        //org.apache.kafka.common.errors.FencedInstanceIdException: The broker rejected this static
        // consumer since another consumer with the same group.instance.id has registered with a different member.id.
        //因为使用的线程池和配置了props.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, "group-instance-id-liebe");
        //造成问题，取消props.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, "group-instance-id-liebe");配置
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-group-id");

        //props.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, "group-instance-id-liebe");

        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, Integer.MAX_VALUE);

        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG,60000);

        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 90000);
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG,3000);

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        props.put(ConsumerConfig.CLIENT_DNS_LOOKUP_CONFIG, "default");

        //开启自动提交
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"false");

        //自动提交间隔
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,6000);

        //props.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, "all");

        //earliest:当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，从头开始消费
        //latest:当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，消费新产生的该分区下的数据
        //none:topic各分区都存在已提交的offset时，从offset后开始消费；只要有一个分区不存在已提交的offset，则抛出异常

        //偏移量设置
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"latest");

        props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG,22129120);

        props.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG,326872323);

        props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG,5000);

        props.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG,30);
        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG,52428800);

        props.put(ConsumerConfig.SEND_BUFFER_CONFIG,52428800);


        props.put(ConsumerConfig.RECEIVE_BUFFER_CONFIG, 52428800);
        //客户端的唯一标识Id
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, "kafka-client-liebe");

        //props.put(ConsumerConfig.CLIENT_RACK_CONFIG,"");

        props.put(ConsumerConfig.RECONNECT_BACKOFF_MS_CONFIG,3000);

        props.put(ConsumerConfig.RECONNECT_BACKOFF_MAX_MS_CONFIG, 5000);
        props.put(ConsumerConfig.RETRY_BACKOFF_MS_CONFIG,3000);

        props.put(ConsumerConfig.METRICS_SAMPLE_WINDOW_MS_CONFIG,6000);

        props.put(ConsumerConfig.METRICS_NUM_SAMPLES_CONFIG, 3);

        props.put(ConsumerConfig.METRICS_RECORDING_LEVEL_CONFIG, "DEBUG");

        //Invalid value 3000000 for configuration metric.reporters: Expected a comma separated list.
        //props.put(ConsumerConfig.METRIC_REPORTER_CLASSES_CONFIG, 3000000);

        //Invalid value CRC32 for configuration check.crcs: Expected value to be either true or false
        props.put(ConsumerConfig.CHECK_CRCS_CONFIG,true);

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,org.apache.kafka.common.serialization.StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,org.apache.kafka.common.serialization.StringDeserializer.class);

        props.put(ConsumerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG,6000);


        props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG,6000);


        props.put(ConsumerConfig.DEFAULT_API_TIMEOUT_MS_CONFIG, 3000);

        // Invalid value true for configuration interceptor.classes: Expected a comma separated list.
        //props.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG,null);

        props.put(ConsumerConfig.EXCLUDE_INTERNAL_TOPICS_CONFIG,"true");

        props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG,"read_committed");
        //允许自动创建主题
        props.put(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG,"true");
        return props;
    }

    @Bean("kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setAckDiscarded(false);
        factory.setConcurrency(10);
        factory.setBatchListener(true);
        factory.setMissingTopicsFatal(false);
        //自动启动需要设置成true
        factory.setAutoStartup(true);
        factory.getContainerProperties().setPollTimeout(3000);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        factory.getContainerProperties().setAckCount(10);
        factory.getContainerProperties().setAckTime(10000);
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }


    /*@Bean(name = "consumerTaskExecutor")
    public ThreadPoolTaskExecutor consumerTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("my-C-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        return executor;
    }*/

    @Bean(name = "listenerTaskExecutor")
    public ThreadPoolTaskExecutor listenerTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("my-L-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        return executor;
    }

    /**
     * kafka消费工厂对象
     * @return
     */
    @Bean
    public ConsumerFactory<Object, Object> consumerFactory() {
        DefaultKafkaConsumerFactory defaultKafkaConsumerFactory = new DefaultKafkaConsumerFactory(consumeConfigs());
        return defaultKafkaConsumerFactory;
    }
}
