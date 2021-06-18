package com.turboic.cloud.delay;
import com.turboic.cloud.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author liebe
 * 测试模板发送消息
 */
@RestController
@RequestMapping("/delay")
@EnableScheduling
public class RabbitMqCustomController {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMqCustomController.class);



    private final SendDelayMessage sendDelayMessage;
    @Autowired
    public RabbitMqCustomController(SendDelayMessage sendDelayMessage) {
        this.sendDelayMessage = sendDelayMessage;
    }

    @RequestMapping("/send")
    public String send(@RequestParam(value="content",required = true) String content){
        logger.info("准备发送消息内容:{}",content);
        boolean result = sendDelayMessage.sendDelayMessage(content);
        if(result){
            return "消息发送成功";
        }
        return "消息发送失败";
    }


    /**
     * 模拟30秒发送一次
     */
    @Scheduled(fixedRate = 1000 * 60)
    public void scheduleSendContent(){
        String time = DateUtils.dateConvertString(new Date());
        String content = UUID.randomUUID().toString() +"-"+ time;
        logger.info("开始发送消息:{}",time);
        boolean result = sendDelayMessage.sendDelayMessage(content);
        if(result){
           logger.info("定时任务发送消息成功");
        }
        else{
            logger.info("定时任务发送消息失败");
        }
    }
}
