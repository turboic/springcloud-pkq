package com.turboic.cloud.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liebe
 *
 * 访问地址
 * http://localhost:9601/dingding
 */
@RestController
@RefreshScope
public class SleuthServiceController {
    private static final Logger logger = LoggerFactory.getLogger(SleuthServiceController.class);
}
