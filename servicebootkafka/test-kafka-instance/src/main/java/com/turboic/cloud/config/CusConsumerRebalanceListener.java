package com.turboic.cloud.config;
import com.turboic.cloud.util.FastJsonUtils;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collection;

public class CusConsumerRebalanceListener implements ConsumerRebalanceListener {
    private static final Logger logger = LoggerFactory.getLogger(CusConsumerRebalanceListener.class);
    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> collection) {
        logger.info("在分区上重新调用:{}", FastJsonUtils.objectToJson(collection));
    }

    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> collection) {
        logger.info("在分区上指派:{}", FastJsonUtils.objectToJson(collection));
    }

    @Override
    public void onPartitionsLost(Collection<TopicPartition> partitions) {
        logger.info("在分区上丢失:{}", FastJsonUtils.objectToJson(partitions));
    }
}
