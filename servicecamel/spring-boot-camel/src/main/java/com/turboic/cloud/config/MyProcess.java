package com.turboic.cloud.config;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义处理
 * @author liebe
 */
public class MyProcess implements Processor {
    private static final Logger logger = LoggerFactory.getLogger(MyProcess.class);
    @Override
    public void process(Exchange exchange) throws Exception {
        Object id = exchange.getIn().getHeader("id");
        logger.error("header参数:{}",id);
        Endpoint fromEndpoint = exchange.getFromEndpoint();
        logger.error("fromEndpoint url :{}",fromEndpoint.getEndpointUri());
        Message message = exchange.getMessage();
        Object object = message.getBody();
        logger.error("message body :{}",object);
    }
}
