package com.turboic.cloud.delay;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
/**
 * @author liebe
 * 自定义输出Output
 */
public interface CustomDelayOutput {
    String MSG_PUBLISHER = "customDelayOutput";
    /**
     * 自定义输出接口的方法
     * @return
     */
    @Output(CustomDelayOutput.MSG_PUBLISHER)
    MessageChannel customDelayOutput();
}