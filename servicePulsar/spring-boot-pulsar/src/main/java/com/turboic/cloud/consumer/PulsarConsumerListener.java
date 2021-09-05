
package com.turboic.cloud.consumer;

import com.turboic.cloud.config.Msg;
import io.github.majusko.pulsar.annotation.PulsarConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.turboic.cloud.constant.Constant.DEFAULT_TOPIC_NAME;

/**
 * @author liebe
 */
@Component
public class PulsarConsumerListener {

    private static final Logger logger = LoggerFactory.getLogger(PulsarConsumerListener.class);

    public PulsarConsumerListener() {
    }

    /**
     *
     * PulsarConsumer注解标注的方法名称必须是
     * consume
     * @param msg
     */
    @PulsarConsumer(topic = DEFAULT_TOPIC_NAME, clazz = Msg.class)
    public void consume(Msg msg) {

        logger.error("Pulsar消息消费成功 = {} ", msg);

    }
}
