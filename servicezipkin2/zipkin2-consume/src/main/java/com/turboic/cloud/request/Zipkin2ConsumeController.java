package com.turboic.cloud.request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author taiji
 */
@RestController
@RequestMapping("/consume")
public class Zipkin2ConsumeController {
    private static final Logger logger = LoggerFactory.getLogger(Zipkin2ConsumeController.class);
    private final RestTemplate restTemplate;

    @Autowired
    public Zipkin2ConsumeController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /***
     * 嵌套调用第三方的服务接口
     * @param name
     * @return
     */
    @RequestMapping("/invoke")
    public String product(@RequestParam(value = "name") String name){
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("name",name);
        String result = restTemplate.postForEntity("http://zipkin2-product/product/hello",map,String.class).getBody();
        logger.info("调用返回结果e:{}",result);
        return result;
    }
}
