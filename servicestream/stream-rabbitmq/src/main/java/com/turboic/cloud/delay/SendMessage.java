package com.turboic.cloud.delay;

/**
 * @author liebe
 */
public interface SendMessage {
    /**
     * 发送指定的消息内容
     * @param content
     * @return
     */
    boolean sendMsg(String content);
}
