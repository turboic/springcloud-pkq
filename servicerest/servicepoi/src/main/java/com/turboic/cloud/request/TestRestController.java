package com.turboic.cloud.request;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

/**
 * @author liebe
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestRestController {
    private static final Logger logger = LoggerFactory.getLogger(TestRestController.class);

    private final RestTemplate restTemplate;
    public TestRestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }



    /***
     * 测试postForEntity方法
     * @return
     */
    @RequestMapping("/exec")
    public Map<String,String> exec(@RequestParam(value = "base64String") String base64String){
        String url = "http://localhost:9999/poi/word";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("base64String", base64String);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity( url, request , Map.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        logger.error("测试方法exec结果HttpStatus:{}",httpStatus.value());
        Map<String,String> stringStringMap = responseEntity.getBody();
        logger.error("测试方法exec结果map:{}",stringStringMap.toString());
        return stringStringMap;
    }
}
