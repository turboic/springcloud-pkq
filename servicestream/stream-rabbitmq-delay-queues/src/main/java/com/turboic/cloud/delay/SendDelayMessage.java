package com.turboic.cloud.delay;

/**
 * @author liebe
 */
public interface SendDelayMessage {
    /**
     * 发送指定的消息内容
     * @param content
     * @return
     */
    boolean sendDelayMessage(String content);
}
