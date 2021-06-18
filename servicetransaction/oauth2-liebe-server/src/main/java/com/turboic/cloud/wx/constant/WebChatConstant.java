package com.turboic.cloud.wx.constant;

/**
 * 定义相关的常量
 * @author liebe
 */
public class WebChatConstant {
    /**
     * 获取code地址
     */
    public static final String oauth2_authorize = "https://open.weixin.qq.com/connect/oauth2/authorize";

    /**
     * 获取access_token地址
     */
    public static final String oauth2_access_token= "https://api.weixin.qq.com/sns/oauth2/access_token";

    /**
     * 授权方式1
     */
    public static final String scope_snsapi_base = "snsapi_base";

    /**
     * 授权方式2
     */
    public static final String scope_snsapi_userinfo = "snsapi_userinfo";
    /**
     * appid
     */
    public static final String appid = "";

    /**
     * secret
     */
    public static final String secret = "";

    public static final String CODE = "CODE";
    public static final String authorization_code = "authorization_code";
    public static final String access_token = "access_token";
    public static final String openid = "openid";
    /**
     *
     * 获取凑得码后跳转的地址
     */
    public static final String redirect_uri_server = "http://adc3c5db5929.ngrok.io/oauth2liebe/access_token";

    public static final String state = "STATE";

    public static final String wechat_redirect = "#wechat_redirect";





}
