
package com.turboic.cloud.config;
import io.github.majusko.pulsar.producer.ProducerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.turboic.cloud.constant.Constant.DEFAULT_TOPIC_NAME;
import static com.turboic.cloud.constant.Constant.STRING_TOPIC_NAME;

/**
 * @author liebe
 */
@Configuration
public class PulsarProducerConfiguration {


    @Bean
    public ProducerFactory producerFactory() {
        return new ProducerFactory()
                .addProducer(DEFAULT_TOPIC_NAME, Msg.class)
                .addProducer(STRING_TOPIC_NAME, String.class);
    }
}
