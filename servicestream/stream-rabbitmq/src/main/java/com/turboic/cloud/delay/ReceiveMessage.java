package com.turboic.cloud.delay;
import org.springframework.messaging.Message;
/***
 *
 * 消息接收接口
 * @author liebe
 */
public interface ReceiveMessage {
    /**
     * 消息接收的接口
     * @param message
     */
    void receiveMsg(Message<String> message);
}
