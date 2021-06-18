package com.turboic.cloud.service.impl;
import com.turboic.cloud.service.KafkaConsumeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumeServiceImpl implements KafkaConsumeService {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumeServiceImpl.class);
    @Override
    public void consume() {

    }
}
