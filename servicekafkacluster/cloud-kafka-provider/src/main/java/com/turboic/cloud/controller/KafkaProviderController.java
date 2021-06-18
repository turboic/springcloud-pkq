package com.turboic.cloud.controller;
import com.turboic.cloud.provider.KafkaProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liebe
 */
@RestController
public class KafkaProviderController {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProviderController.class);

    private final KafkaProviderService kafkaProviderService;

    public KafkaProviderController(KafkaProviderService kafkaProviderService) {
        this.kafkaProviderService = kafkaProviderService;
    }

    @RequestMapping(value = "/provider")
    public String invokeProviderMethod(@RequestParam(value = "msg") String msg){
        logger.info("后台接收参数:{}",msg);
        logger.info("准备调用kafka producer 向 kafka broker 发送消息:{}",msg);
        String json = "{\"name\":\""+msg+"\"}";
        boolean result = kafkaProviderService.provider(json,0);
        if(result){
            logger.info("kafka producer 生产者消息发送成功");
            return "kafka producer 生产者消息发送成功";
        }
        else{
            logger.info("kafka producer 生产者消息发送失败");
            return "kafka producer 生产者消息发送失败";
        }
    }
}
