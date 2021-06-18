package com.turboic.cloud.delay;
import org.springframework.messaging.Message;
/***
 *
 * 消息接收接口
 * @author liebe
 */
public interface ReceiveDelayMessage {
    /**
     * 消息接收的接口
     * @param message
     */
    void receiveDelayMessage(Message<String> message);
}
