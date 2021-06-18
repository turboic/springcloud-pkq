package com.turboic.cloud.delay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author liebe
 * 绑定输出的通道
 */
@EnableBinding(CustomOutput.class)
public class SendMessageImpl implements SendMessage{
    private final static Logger logger = LoggerFactory.getLogger(SendMessageImpl.class);
    private String format = "yyyy-mm-dd  HH:mm:ss";
    /**
     * 发送指定的消息内容
     * @param content
     * @return
     */

    private final CustomOutput customOutput;
    @Autowired
    public SendMessageImpl(CustomOutput customOutput) {
        this.customOutput = customOutput;
    }

    @Override
    public boolean sendMsg(String content) {
        boolean result;
        try {
            result = this.customOutput.customOutput().send(MessageBuilder.withPayload(content).build());
            if (result) {
                logger.error("消息发送成功:{}", content);
            }
        } catch (Exception e) {
            logger.error("消息发送失败:{}", e);
            result = false;
        }
        return result;
    }

    /***
     * 轮询发送消息
     * @return
     */
   /* @Bean
    @InboundChannelAdapter(value = CustomOutput.MSG_PUBLISHER, poller = @Poller(fixedDelay = "200000", maxMessagesPerPoll = "1"))
    public MessageSource<String> sendTime() {
        logger.info("Hi 皮卡丘，这里是spring cloud stream rabbitmq消息轮询发送机制,明天加班"+new SimpleDateFormat(format).format(new Date()));
        return () -> new GenericMessage<>("Hi 皮卡丘，这里是spring cloud stream rabbitmq消息轮询发送机制,明天加班"+new SimpleDateFormat(format).format(new Date()));
    }*/
}
