package com.turboic.cloud.delay;
import com.turboic.cloud.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

import java.util.Date;

/***
 *
 * 消息接收接口
 * 绑定输入的通道
 * @author liebe
 */
@EnableBinding(CustomDelayInput.class)
public class ReceiveMessageImpl implements ReceiveDelayMessage{
    private final static Logger logger = LoggerFactory.getLogger(ReceiveMessageImpl.class);
    /**
     * 消息接收的接口
     * 监控消息通道
     * @param message
     */
    @Override
    @StreamListener(CustomDelayInput.MSG_CONSUME)
    public void receiveDelayMessage(Message<String> message) {
        /***logger.info("stream rabbitmq-server listener delay message !");***/
        String s = message.getPayload();
        logger.info("客户端接收时间:{}",DateUtils.dateConvertString(new Date()));
        logger.debug(s);
        //logger.info("[rabbitmq消息延迟队列的方式接收消息内容]"+ DateUtils.dateConvertString(new Date())+"："+s);
    }
}
