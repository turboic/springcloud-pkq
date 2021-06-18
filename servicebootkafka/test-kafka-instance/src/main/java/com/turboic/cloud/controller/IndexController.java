package com.turboic.cloud.controller;

import com.turboic.cloud.util.DynamicRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.xml.ws.soap.Addressing;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class IndexController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);
    private static final String HOME_PAGE = "index";
    private static final String MAIN_PAGE = "index";

    public IndexController(DynamicRunnable dynamicRunnable) {
        this.dynamicRunnable = dynamicRunnable;
    }

    @RequestMapping("/")
    public String index() {
        log.info("访问首页跳转");
        return HOME_PAGE;
    }

    private final DynamicRunnable dynamicRunnable;

    /***
     * Whitelabel Error Page
     * This application has no explicit mapping for /error, so you are seeing this as a fallback.
     *
     * Wed Apr 14 23:53:23 GMT+08:00 2021
     * There was an unexpected error (type=Not Found, status=404).
     * No message available
     * @return
     */
    @RequestMapping("/main")
    public String main() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(dynamicRunnable);
        log.info("访问main跳转");
        return MAIN_PAGE;
    }
}
