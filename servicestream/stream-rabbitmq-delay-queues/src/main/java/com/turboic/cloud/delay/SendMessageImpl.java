package com.turboic.cloud.delay;
import com.turboic.cloud.utils.DateUtils;
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
@EnableBinding(CustomDelayOutput.class)
public class SendMessageImpl implements SendDelayMessage{
    private final static Logger logger = LoggerFactory.getLogger(SendMessageImpl.class);
    private String format = "yyyy-mm-dd  HH:mm:ss";
    /**
     * 发送指定的消息内容
     * @param content
     * @return
     */

    private final CustomDelayOutput customDelayOutput;

    @Autowired
    public SendMessageImpl(CustomDelayOutput customDelayOutput) {
        this.customDelayOutput = customDelayOutput;
    }


    @Override
    public boolean sendDelayMessage(String content) {
        boolean result;
        try {
            /**
             * 设置演示队列头，30秒发送一次，15秒的延迟接收
             * x-delay值固定，后面的是毫秒值
             */
            result = this.customDelayOutput.customDelayOutput().
                    send(MessageBuilder.withPayload(content).setHeader("x-delay",1000*30)
                    .build());
            if (result) {
                logger.error("设置消息延迟,准备推送到rabbitmq-server:{}", DateUtils.dateConvertString(new Date()));
            }
        } catch (Exception e) {
            logger.error("消息发送失败:{}", e);
            result = false;
        }
        return result;
    }
}
