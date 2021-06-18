package com.turboic.cloud.wx.controller.login;

import com.turboic.cloud.wx.constant.WebChatConstant;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 微信登录流程
 * @author liebe
 */
@Api(value="第一步，服务端请求微信的认证接口，获取授权的code值",tags={"第一步：用户同意授权，获取code"})
@Controller
public class WebChatOauthCodeController {
    private static final Logger logger = LoggerFactory.getLogger(WebChatOauthCodeController.class);

    private final RestTemplate restTemplate;
    public WebChatOauthCodeController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /***
     *第一步：用户同意授权，获取code
     */

    @GetMapping("/code")
    public String code(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

        String remoteAddr = httpServletRequest.getRemoteAddr();
        logger.error("第一步：用户同意授权，获取code,请求结果remoteAddr:{}",remoteAddr);
        String requestURI = httpServletRequest.getRequestURI();
        logger.error("第一步：用户同意授权，获取code,请求结果requestURI:{}",requestURI);
        String requestURL = httpServletRequest.getRequestURL().toString();
        logger.error("第一步：用户同意授权，获取code,请求结果requestURL:{}",requestURL);
        String redirect_uri;
        try {
            redirect_uri = URLEncoder.encode(WebChatConstant.redirect_uri_server, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            redirect_uri = WebChatConstant.redirect_uri_server;
            logger.error("redirect_uri编码异常:{}",e);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(WebChatConstant.oauth2_authorize);
        stringBuilder.append("?appid="+WebChatConstant.appid).append("&").append("redirect_uri="+redirect_uri);
        stringBuilder.append("&response_type=code");
        stringBuilder.append("&scope="+WebChatConstant.scope_snsapi_userinfo);
        stringBuilder.append("&state="+WebChatConstant.state+WebChatConstant.wechat_redirect);
        logger.error("第一步：用户同意授权，获取code,请求url:{}",stringBuilder.toString());
        logger.error("第一步：用户同意授权，获取code,url地址开始转向腾讯服务器:{}","哈哈哈");
        return "redirect:"+stringBuilder.toString();
    }
}
