package com.turboic.cloud.wx.controller.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.turboic.cloud.wx.constant.WebChatConstant;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * 微信登录流程
 * @author liebe
 */
@Api(value="第一步，服务端请求微信的认证接口，获取授权的code值",tags={"第一步：用户同意授权，获取code"})
@RestController
public class WebChatOauth2LoginController {
    private static final Logger logger = LoggerFactory.getLogger(WebChatOauth2LoginController.class);

    private final RestTemplate restTemplate;
    public WebChatOauth2LoginController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /***
     *第一步：用户同意授权，获取code
     */



    /***
     * 第二步：通过code换取网页授权access_token
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     */
    @RequestMapping("/access_token")
    public String access_token(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

        String remoteAddr = httpServletRequest.getRemoteAddr();
        logger.error("第二步：通过code换取网页授权access_token,请求结果remoteAddr:{}",remoteAddr);
        String requestURI = httpServletRequest.getRequestURI();
        logger.error("第二步：通过code换取网页授权access_token,请求结果requestURI:{}",requestURI);
        String requestURL = httpServletRequest.getRequestURL().toString();
        logger.error("第二步：通过code换取网页授权access_token,请求结果requestURL:{}",requestURL);


        String code = httpServletRequest.getParameter("code");

        String state = httpServletRequest.getParameter("state");


        logger.error("第二步：通过code换取网页授权access_token，微信服务器返回的code值:{}",code);
        logger.error("第二步：通过code换取网页授权access_token，微信服务器返回的state值:{}",state);

        StringBuilder url = new StringBuilder();
        url.append(WebChatConstant.oauth2_access_token);
        url.append("?appid="+WebChatConstant.appid);
        url.append("&secret=").append(WebChatConstant.secret);
        url.append("&code=").append(code);
        url.append("&grant_type=").append(WebChatConstant.authorization_code);

        logger.error("第二步：通过code换取网页授权access_token，向腾讯微信服务器请求的url:{}",url.toString());

        String accessTokenResult = restTemplate.getForObject(url.toString(),String.class);

        logger.error("第二步：通过code换取网页授权access_token，腾讯微信服务器响应结果:{}",accessTokenResult);

        JSONObject map = JSON.parseObject(accessTokenResult);
        if(map != null && map.containsKey(WebChatConstant.access_token)){
            logger.error("第二步：通过code换取网页授权access_token，获取access_token成功:{}",map.get(WebChatConstant.access_token));

            String access_token = map.get(WebChatConstant.access_token).toString();
            String openid = map.get(WebChatConstant.openid).toString();

            logger.error("第二步：通过code换取网页授权access_token，获取access_token成功:{},参数access_token:{},openid:{}",
                    map.get(WebChatConstant.access_token),access_token,openid);

            return userinfo(access_token,openid);
        }
        else{
            logger.error("第二步：通过code换取网页授权access_token，获取access_token失败:{}",accessTokenResult);
            return null;
        }
    }

    /**
     * 第四步：拉取用户信息(需scope为 snsapi_userinfo)
     * @param access_token
     * @return
     */

    private String userinfo(String access_token,String openid){
        String ipList = shorturl(access_token);
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
        String result = restTemplate.getForObject(url,String.class);
        try {
            result = new String(result.getBytes("ISO-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("第四步：拉取用户信息(需scope为 snsapi_userinfo),UnsupportedEncodingException:{}",e);
        }
        logger.error("第四步：拉取用户信息(需scope为 snsapi_userinfo),userinfo:{}",result);
        return result+ipList;
    }


    private String getcallbackip(String access_token){
        String url = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token="+access_token;
        String ipList = restTemplate.getForObject(url,String.class);
        try {
            ipList = new String(ipList.getBytes("ISO-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("获取微信服务器ip地址异常,UnsupportedEncodingException:{}",e);
        }
        logger.error("获取微信服务器ip地址，ip:{}",ipList);
        return ipList;
    }

    @GetMapping("/test")
    public Object test(){
        return "服务请求成功";
    }

    private String shorturl(String access_token){
        logger.error("com.netflix.discovery.shared.transport.TransportException: Cannot execute request on any known server");
        String url = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token="+access_token;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("action", "long2short");
        map.add("long_url", url);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity( url, request , String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        logger.error("获取微信服务器shorturl地址，shorturl:{}",responseEntity.getBody());
        return responseEntity.getBody();
    }





}
