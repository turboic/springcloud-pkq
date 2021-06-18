package com.turboic.cloud.controller;
import com.turboic.cloud.service.KafkaInstanceService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * jpa-service的controller
 * @author liebe
 */
@Api(value="kafka消息发送接口类",tags={"消息生产者"})
@RestController
@RequestMapping("/producer")
public class KafkaProducerController {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerController.class);

    private final KafkaInstanceService kafkaInstanceService;

    public KafkaProducerController(KafkaInstanceService kafkaInstanceService) {
        this.kafkaInstanceService = kafkaInstanceService;
    }


    /**
     *
     * @param msg
     * @return
     */
    @RequestMapping("/send")
    public String send(String msg){
        kafkaInstanceService.send(msg);
        return "发送完成";
    }
}
