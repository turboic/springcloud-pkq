
package com.turboic.cloud.producer;

import cn.hutool.json.JSONUtil;
import com.turboic.cloud.config.Msg;
import io.github.majusko.pulsar.producer.PulsarTemplate;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.turboic.cloud.constant.Constant.DEFAULT_TOPIC_NAME;

/**
 * @author liebe
 */
@Service
public class PulsarProducerService {
    private static final Logger logger = LoggerFactory.getLogger(PulsarProducerService.class);

    @Autowired
    private PulsarTemplate<Msg> pulsarProducer;

    public PulsarProducerService() {
    }

    public void send(Msg msg) {
        try {
            pulsarProducer.send(DEFAULT_TOPIC_NAME, msg);
            logger.error("pulsar消息发送成功", JSONUtil.parseObj(msg).toString());
        } catch (PulsarClientException e) {
            logger.info("PulsarClientException = {}",e);
        }
    }
}
