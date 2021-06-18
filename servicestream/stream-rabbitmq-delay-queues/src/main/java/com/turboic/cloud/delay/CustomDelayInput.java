package com.turboic.cloud.delay;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;
/**
 * @author liebe
 * 自定义输入CustomInput
 */
public interface CustomDelayInput {
    String MSG_CONSUME = "customDelayInput";
    /**
     * 自定义输入接口的方法
     * 这里的注解千万不要写错了@Output,
     * 否则会出现能发消息，收不到消息的情况
     * @return
     */
    @Input(CustomDelayInput.MSG_CONSUME)
    SubscribableChannel inputOutput();
}