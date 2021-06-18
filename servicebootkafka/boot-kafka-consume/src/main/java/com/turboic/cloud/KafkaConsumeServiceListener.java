package com.turboic.cloud;
import com.turboic.cloud.mapper.MessageRecordMapper;
import com.turboic.cloud.pojo.MessageRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.Base64;
import java.util.Date;
/**
 * @author liebe
 * kafka的消费监听类
 */
@Component
@Slf4j
public class KafkaConsumeServiceListener {

    private final MessageRecordMapper messageRecordMapper;
    private static final String default_transform_type = "text";
    private static final int default_transform_type_index = 2;
    private static final int default_transform_content_index = 1;

    public KafkaConsumeServiceListener(MessageRecordMapper messageRecordMapper) {
        this.messageRecordMapper = messageRecordMapper;
    }

    @KafkaListener(id=KafkaConstant.default_group,topics = KafkaConstant.default_topic)
    public void kafkaListener(String receiveMessage) {
        log.info("kafka 消费端接收消息内容为: [{}]", receiveMessage);
        updateMessageRecord(receiveMessage);
    }

    /***
     * 数据库持久化更新操作
     * @param receiveMessage
     * @return
     */
    private int updateMessageRecord(String receiveMessage){
        int updateResult = -1;
        if(StringUtils.hasText(receiveMessage)){
            String [] messages = receiveMessage.split("@");
            if(messages != null && messages.length > 0){
                String persistMessage = null;
                if(!messages[default_transform_type_index].equalsIgnoreCase(default_transform_type)){
                    try {
                        stringSerializFile(messages[default_transform_content_index],messages[default_transform_type_index]);
                    } catch (IOException e) {
                        log.error("kafka消费端接收jpg类型数据序列化异常:{}",e);
                        /*** 返回不执行下面的操作***/
                        return -1;
                    }
                }
                MessageRecord messageRecord = messageRecordMapper.selectById(messages[0]);
                if(messageRecord != null){
                    messageRecord.setStatus(KafkaConstant.KafKaPersistStatus.CONSUME_RECEIVE.getStatus());
                    messageRecord.setConsumeDate(new Date());
                    updateResult = messageRecordMapper.updateById(messageRecord);
                    if(updateResult > 0){
                        log.info("接收消息，数据库持久化更新成功");
                    }
                    else{
                        log.error("接收消息，数据库持久化更新失败");
                    }
                }
                else{
                    log.error("接收消息，数据库没有对应的存储记录不能更新");
                }
            }
        }
        return updateResult;
    }

    /**
     * 字符串转换成图片
     * @param persistMessage
     * @return
     * @throws IOException
     */
    private File stringSerializFile(String persistMessage,String fileName) throws IOException {
        log.error(new String(Base64.getMimeDecoder().decode("5oqW6Z+zMjAyMDA1MzE="), "utf-8"));
        if(StringUtils.hasText(persistMessage)){
            String text = new String(Base64.getMimeDecoder().decode(persistMessage), "utf-8");
            if(!StringUtils.hasText(text)){
                return null;
            }
            //String tempPath =System.getProperty("java.io.tmpdir")+File.separator;
            String tempPath = "C:\\Users\\taiji\\Desktop";
            File file = new File(tempPath+File.separator+"_"+fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            log.info("接收文件保存本地磁盘空间的路径:{}",file.getAbsolutePath());
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] b = text.getBytes("utf-8");
            fileOutputStream.write(b,0,b.length);
            fileOutputStream.flush();
            if(fileOutputStream != null){
                fileOutputStream.close();
            }
            return file;
        }
        return null;
    }
}