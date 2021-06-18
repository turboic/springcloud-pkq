package com.turboic.cloud.confirm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.UUID;

/**
 * @author liebe
 */
@Component
public class RabbitMqProducerConfirm implements  RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback{
    private static Logger log = LoggerFactory.getLogger(RabbitMqProducerConfirm.class);
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMqProducerConfirm(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        //这是是设置回调能收到发送到响应
        rabbitTemplate.setConfirmCallback(this);
        //如果设置备份队列则不起作用
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(this);
    }

    /**
     * 发送消息
     * @param message
     */
    public void send(String message) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        String sendMsg = message + new Date();
        log.info("rabbitmq消息确认机制发送消息:{} ",sendMsg);
        rabbitTemplate.convertAndSend("exchange.hello","helloKey", sendMsg,correlationId);
        log.info("rabbitmq消息确认机制发送消息完成");
    }


    /***
     * 回调确认
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if(ack){
            log.info("消息发送成功:correlationData({}),ack({}),cause({})",correlationData,ack,cause);
        }else{
            log.info("消息发送失败:correlationData({}),ack({}),cause({})",correlationData,ack,cause);
        }
    }

    /**
     *  消息发送到转换器的时候没有对列,配置了备份对列该回调则不生效
     */

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",exchange,routingKey,replyCode,replyText,message);
    }
}