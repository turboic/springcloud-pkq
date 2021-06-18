package com.turboic.cloud.delay;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
/**
 * @author liebe
 * 自定义输出Output
 */
public interface CustomOutput {
    String MSG_PUBLISHER = "customOutput";
    /**
     * 自定义输出接口的方法
     * @return
     */
    @Output(CustomOutput.MSG_PUBLISHER)
    MessageChannel customOutput();
}