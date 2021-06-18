package com.turboic.cloud;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liebe
 * 测试模板发送消息
 */
@RestController
@RequestMapping("/rabbitmq")
public class RabbitMqSendController {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMqSendController.class);
    /*** 默认连接localhost:5762的rabbitmq-server***/
    private final AmqpTemplate amqpTemplate;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMqSendController(AmqpTemplate amqpTemplate, RabbitTemplate rabbitTemplate) {
        this.amqpTemplate = amqpTemplate;
        this.rabbitTemplate = rabbitTemplate;
    }

    /***
     * 发送出现了错误
     * No converter found from actual payload type 'byte[]' to expected payload type 'java.lang.String'
     *
     * Channel shutdown: channel error; protocol method: #method<channel.close>(reply-code=404, reply-text=NOT_FOUND - no exchange 'DLX' in vhost '/', class-id=60, method-id=40)
     * @param name
     * @return
     */
    @RequestMapping("/send")
    public String send(@RequestParam(value="name",required = false) String name){
        logger.info("receive http request");
        if(StringUtils.isEmpty(name)){
            name = "Welcome liebe to WebChat";
        }

        /***创建使用默认的MessageProperties***/

        Message message = new Message(name.getBytes(),new MessageProperties());
        /***
         * String exchange,
         * String routingKey
         */
        String routingKey = "pkq";
        logger.info("starting send rabbitmq message");
        String host = rabbitTemplate.getConnectionFactory().getHost();
        logger.info("使用rabbitmq模板发送消息，默认连接的主机host是:{}",host);
        rabbitTemplate.send(message);
        //下面的发送失败
        //rabbitTemplate.send("liebe",routingKey,message);
        logger.info("stopping send rabbitmq message");
        return name;
    }


}
