package com.turboic.cloud.request;

import com.turboic.cloud.constant.KafkaConstant;
import com.turboic.cloud.service.KafkaProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.ExecutionException;

/**
 * @author liebe
 */

@RestController
@Slf4j
public class KafkaProviderController {
    private final KafkaTemplate kafkaTemplate;

    private final KafkaProductService kafkaProductService;

    private final static int num = 100;

    @Autowired
    public KafkaProviderController(KafkaTemplate kafkaTemplate, KafkaProductService kafkaProductService) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaProductService = kafkaProductService;
    }


    /**
     * kafka同步发送与异步发送消息
     */


    /**
     * 同步发送
     *
     * @return

     */
    @RequestMapping("sync")
    public String syncSendMessage() {
        for (int i = 0; i < num; i++) {
            try {
                kafkaTemplate.send(KafkaConstant.default_topic, "0", "foo" + i).get();
            } catch (InterruptedException e) {
                log.error("sync send message fail [{}]", e.getMessage());
                e.printStackTrace();
            } catch (ExecutionException e) {
                log.error("sync send message fail [{}]", e.getMessage());
                e.printStackTrace();
            }
        }
        return "success";
    }

    /**
     * 异步发送
     *
     * @return
     */
    @RequestMapping("async")
    public String sendMessageAsync() {
        for (int i = 0; i < num; i++) {
            /**
             * <p>
             * SendResult:如果消息成功写入kafka就会返回一个RecordMetaData对象;result.
             * getRecordMetadata() 他包含主题信息和分区信息，以及集成在分区里的偏移量。
             * 查看RecordMetaData属性字段就知道了
             * </p>
             *
             */
            ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(KafkaConstant.default_topic, "0", "foo" + i);
            send.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onSuccess(SendResult<String, String> result) {
                    log.info("async send message success partition [{}]", result.getRecordMetadata().partition());
                    log.info("async send message success offest[{}]", result.getRecordMetadata().offset());
                }
                @Override
                public void onFailure(Throwable ex) {
                    log.error("async send message fail [{}]", ex.getMessage());

                }
            });
        }
        return "发送成功";
    }

    @RequestMapping("/provider")
    public String product(@RequestParam(value = "msg") String msg){
        log.info("Controller接收消息:{}",msg);
        kafkaProductService.product(msg,"text");
        return "发送成功";
    }
}
