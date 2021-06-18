package com.turboic.cloud.config;
import com.netflix.discovery.converters.Auto;
import com.turboic.cloud.util.CustomRunnable;
import com.turboic.cloud.util.RequestWeather;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/***
 * spring-boot应用启动完成过执行
 */
@Component
public class StartConsumeInstanceThread implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(StartConsumeInstanceThread.class);
    private final RequestWeather requestWeather;
    public StartConsumeInstanceThread(RequestWeather requestWeather) {
        this.requestWeather = requestWeather;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("spring boot程序初始化后执行程序调用");
        //logger.info(this.name + "线程-" + this.name + "开始执行run方法");
        boolean startThreadConfirm = false;//禁用，不开启
        if(startThreadConfirm){
            KafkaConsumeConfig kafkaConsumeConfig = null;
            if (kafkaConsumeConfig == null) {
                kafkaConsumeConfig = new KafkaConsumeConfig();
                logger.info("初始化kafkaConsumeConfig"+(kafkaConsumeConfig == null));
            }
            KafkaConsumer<String, String> kafkaConsumer = null;
            if (kafkaConsumer == null) {
                kafkaConsumer = kafkaConsumeConfig.kafkaConsumer();
            }
            CustomRunnable customRunnable1 = new CustomRunnable("线程1",kafkaConsumer,requestWeather);
            //CustomRunnable customRunnable2 = new CustomRunnable("线程2");

            Thread t1 = new Thread(customRunnable1);
            //Thread t2 = new Thread(customRunnable2);
            t1.start();
            //t2.start();
            logger.info("spring boot程序初始化后线程启动完毕");
        }
    }
}
