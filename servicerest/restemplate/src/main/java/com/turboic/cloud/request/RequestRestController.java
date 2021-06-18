package com.turboic.cloud.request;
import com.turboic.cloud.entity.User;
import com.turboic.cloud.util.FastJsonUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * jpa-service的controller
 * @author liebe
 */
@Api(value="jpa transaction invoke的controller",tags={"jpa事务的接口"})
@RestController
@RequestMapping("/request")
@Slf4j
public class RequestRestController {
    private static final Logger logger = LoggerFactory.getLogger(RequestRestController.class);

    private final RestTemplate restTemplate;
    public RequestRestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    /**
     * 测试getForObject方法
     * @param name
     * @return
     */
    @RequestMapping("/getForObject")
    public String getForObject(String name){
        String url = "http://localhost:9993/response/getForObject?name="+name;
        String result = restTemplate.getForObject(url,String.class);
        logger.error("测试方法getForObject结果result:{}",result);
        return result;
    }


    /***
     * 测试postForEntity方法
     * @return
     */
    @RequestMapping("/postForEntity")
    public User postForEntity(){
        String url = "http://localhost:9993/response/postForEntity";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("username", "xxxxxxxxxx@qq.com");
        map.add("password", "123456");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<User> responseEntity = restTemplate.postForEntity( url, request , User.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        logger.error("测试方法postForEntity结果HttpStatus:{}",httpStatus.value());
        User user = responseEntity.getBody();
        logger.error("测试方法postForEntity结果user:{}",user.toString());
        return user;
    }


    /**
     * 测试delete方法
     * @return
     */
    @RequestMapping("/delete")
    public String delete(){
        String url = "http://localhost:9993/response/delete/{1}/{2}";
        log.error("请求url地址:{}",url);
        Object [] uriVariables = new Object[]{"admin","123456"};
        restTemplate.delete(url,uriVariables);
        logger.error("测试方法delete结果result","成功");
        return "delete方法调用成";
    }



    /**
     * 测试exchange方法
     * @return
     */
    @RequestMapping("/exchange")
    public String exchange(){
        String url = "http://localhost:9993/response/exchange/{1}/{2}";
        log.error("请求url地址:{}",url);

        Map<String,String> uriVariables = new HashMap<>(2);
        uriVariables.put("1", "admin");
        uriVariables.put("2", "123456");

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("fileId", UUID.randomUUID().toString());
        map.add("fileName", "静止.jpg");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setConnection("keep-alive");

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);
        /**
         * post能接收全部
         * get能接收uriVariables
         */
        ResponseEntity<List<String>> responseEntity = restTemplate.exchange(url,HttpMethod.POST,httpEntity,
                new ParameterizedTypeReference<List<String>>(){},uriVariables);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        logger.error("测试方法exchange结果HttpStatus:{}",httpStatus.value());
        List<String> result = responseEntity.getBody();
        logger.error("测试方法exchange结果result:{}",result);
        return FastJsonUtils.objectToJson(result);
    }


    /**
     * execute
     * @return
     */
    @RequestMapping("/execute")
    public String execute(){
        String url = "http://localhost:9993/response/execute/{1}/{2}";
        log.error("请求url地址:{}",url);
        ResponseExtractor responseExtractor = restTemplate.responseEntityExtractor(String.class);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("server", "soul-Server");
        map.add("client", "HttpClient");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setConnection("keep-alive");
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);

        RequestCallback requestCallback = restTemplate.httpEntityCallback(httpEntity,String.class);

        Map<String,String> uriVariables = new HashMap<>(2);
        uriVariables.put("1", "admin");
        uriVariables.put("2", "123456");

        ResponseEntity responseEntity = (ResponseEntity) restTemplate.execute(url,HttpMethod.POST,requestCallback,responseExtractor,uriVariables);

        HttpStatus httpStatus = responseEntity.getStatusCode();
        logger.error("测试方法execute结果httpStatus:{}",httpStatus.value());

        String result = (String) responseEntity.getBody();
        logger.error("测试方法execute结果result:{}",result);
        return result;
    }




}
