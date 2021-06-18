package com.turboic.cloud.util;

import com.turboic.cloud.KafkaInstanceApplication;
import com.turboic.cloud.config.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RequestWeather {
    private final RestTemplate restTemplate;
    private static final Logger log = LoggerFactory.getLogger(KafkaInstanceApplication.class);

    public RequestWeather(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 城市中文名称
     * @param city
     * @return
     */
    public void weather(String city){
        log.info("city={}",city);
        String url = String.format(Constant.WEATHER_URL,city);
        String result = restTemplate.getForObject(url,String.class);
        log.info("result={}",result);
        System.err.println(result);
    }
}
