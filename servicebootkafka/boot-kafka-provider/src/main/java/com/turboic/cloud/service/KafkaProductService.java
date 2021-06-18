package com.turboic.cloud.service;
import com.turboic.cloud.constant.KafkaConstant;
import com.turboic.cloud.mapper.MessageRecordMapper;
import com.turboic.cloud.pojo.MessageRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * @author liebe
 */
@Service
@Slf4j
public class KafkaProductService {
    private final KafkaTemplate kafkaTemplate;
    @Autowired
    public KafkaProductService(KafkaTemplate kafkaTemplate, MessageRecordMapper messageRecordMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.messageRecordMapper = messageRecordMapper;
    }

    public final MessageRecordMapper messageRecordMapper;

    /***
     * 异步发送
     * 消息超长，producer没有收到回调信息
     * @param message
     * @param type
     */
    public void product(String message,String type) {
        if(StringUtils.isEmpty(message)){
            message = "kafka发送的消息为空!";
        }
        String uuid = UUID.randomUUID().toString();
        String kafkaMsg = uuid+"@"+message+"@"+type;
        log.error("kafka product发送消息内容:{}",kafkaMsg);
        ListenableFuture listenableFuture = kafkaTemplate.send(KafkaConstant.default_topic,kafkaMsg);
        String finalMessage = message;
        listenableFuture.addCallback(
                o -> {
                    //此处设置product设置的机制是kafka集群中所有节点都接收到消息，才算发送成功，All
                    saveMessageRecord(uuid, finalMessage);
                    log.info("生产者向Kafka Broker 消息发送成功,{}", o.toString());
                },
                //生产者向Kafka Broker 消息发送失败,{}Failed to send;
                // nested exception is org.apache.kafka.common.errors.TimeoutException:
                // Topic liebe not present in metadata after 60000 ms.
                throwable -> {
                    log.info("生产者向Kafka Broker 消息发送失败,{}" + throwable.getMessage());
                    log.info("生产者向Kafka Broker 消息发送失败!+1");
                    log.info("生产者向Kafka Broker 消息发送失败!+2");
                    log.info("生产者向Kafka Broker 消息发送失败!+3");
                }
        );

    }

    private int saveMessageRecord(String uuid,String message){
        MessageRecord messageRecord = new MessageRecord();
        messageRecord.setId(uuid);
        messageRecord.setStatus(KafkaConstant.KafKaPersistStatus.PRODUCT_SEND.getStatus());
        messageRecord.setProductDate(new Date());
        messageRecord.setContent(message);
        int insertResult = messageRecordMapper.insert(messageRecord);
        if(insertResult > 0){
            log.info("发送消息，数据库持久化成功");
        }
        else{
            log.error("发送消息，数据库持久化失败");
        }
        return insertResult;
    }

    /**
     * file转换成字符串发送kafka消息
     * @param file
     */
    public void jpg(MultipartFile file) {
        String message = null;
        try {
            message = fileSerializString(file);
        } catch (IOException e) {
            log.error("jpg转换成base64编码失败:{}",e);
            return;
        }
        product(message,file.getOriginalFilename());
    }

    /**
     * 文件图片转换成字符串
     * @param file
     * @return
     * @throws IOException
     */
    private String fileSerializString(MultipartFile file) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = file.getInputStream();
        if(inputStream != null){
            int length;
            byte [] b = new byte[2048];
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while((length = inputStream.read(b))!=-1) {
                bos.write(b, 0, length);
            }
            bos.close();
            stringBuilder.append(new String(bos.toByteArray(),0,bos.size(),"utf-8"));
        }
        log.error(Base64.getEncoder().encodeToString("抖音20200531".getBytes("utf-8")));
        String base64String = Base64.getEncoder().encodeToString(stringBuilder.toString().getBytes("utf-8"));
        return base64String;
    }
}
