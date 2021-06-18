package com.turboic.cloud.wx.controller.translatecontent;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 微信登录流程
 * @author liebe
 */
@Api(value="第一步，服务端请求微信的认证接口，获取授权的code值",tags={"第一步：用户同意授权，获取code"})
@Controller
public class WebChatTranslateContentController {
    private static final Logger logger = LoggerFactory.getLogger(WebChatTranslateContentController.class);

    private static final String weiZhi = "我不知道你说的是什么";

    private final RestTemplate restTemplate;
    public WebChatTranslateContentController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /***
     *第一步：用户同意授权，获取code
     */

    @PostMapping("/translateContent")
    public String translateContent(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        String access_token = httpServletRequest.getParameter("token");;
        String url = "http://api.weixin.qq.com/cgi-bin/media/voice/translatecontent?access_token=ACCESS_TOKEN&lfrom=zh_CN&lto=en_US";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String text = httpServletRequest.getParameter("text");
        HttpEntity<byte[]> httpEntity = new HttpEntity(text.getBytes(), headers);
        HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        if (response != null){
            String message = response.getBody();
            logger.error("返回结果:{}",message);
            return message;
        }
        else{
            return weiZhi;
        }
    }
}
