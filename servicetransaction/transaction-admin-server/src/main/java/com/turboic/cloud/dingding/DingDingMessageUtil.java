package com.turboic.cloud.dingding;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Liebe
 */
@Component
@RefreshScope
public class DingDingMessageUtil {
    private static final Logger logger = LoggerFactory.getLogger(DingDingMessageUtil.class);
    @Value("${dingding.secret}")
    private String secret;
    @Value("${dingding.urlAccessToken}")
    private String urlAccessToken;

    private static final String defaultContext = "@所有人,";

    public String sendTextMessage(String msg) {
        logger.info("开始调用sendTextMessage方法:{}",msg);
        if(StringUtils.isEmpty(secret)){
            return "加签内容secret为空";
        }
        Long timestamp = System.currentTimeMillis();
        String sign;
        try{
           sign = SecretCrypto.crypto(secret,timestamp);
        }
        catch (Exception e){
            logger.error("签名加密出现异常:{}",e);
            return "签名加密出现异常"+e;
        }
        if(StringUtils.isEmpty(sign)){
            return "签名sign为空";
        }

        String url3 = urlAccessToken+"&timestamp="+timestamp+"&sign="+sign;
        logger.info("请求钉钉接口url:{}",url3);

        DingTalkClient client = new DefaultDingTalkClient(url3);
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("text");
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent(defaultContext+msg);
        request.setText(text);

        //@所有人
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setIsAtAll(true);
        request.setAt(at);

        OapiRobotSendResponse oapiRobotSendResponse = null;
        try {
            oapiRobotSendResponse = client.execute(request);
            if(oapiRobotSendResponse != null && oapiRobotSendResponse.isSuccess()){
                logger.info("钉钉接口调用成功:{},:{}",oapiRobotSendResponse.getBody(),oapiRobotSendResponse.getMessage());
            }
        } catch (ApiException e) {
            logger.error("调用钉钉接口出现错误:{}",e);
        }
        return JSON.toJSONString(oapiRobotSendResponse);
    }


    public String sendMarkdownMessage(String markdownMessage) {
        logger.info("开始调用sendMarkdownMessage方法:{}",markdownMessage);
        if(StringUtils.isEmpty(secret)){
            return "markdown加签内容secret为空";
        }
        Long timestamp = System.currentTimeMillis();
        String sign;
        try{
            sign = SecretCrypto.crypto(secret,timestamp);
        }
        catch (Exception e){
            logger.error("markdown签名加密出现异常:{}",e);
            return "markdown签名加密出现异常"+e;
        }
        if(timestamp == null){
            return "markdown时间戳timestamp为空";
        }
        if(StringUtils.isEmpty(sign)){
            return "markdown签名sign为空";
        }

        String url = urlAccessToken+"&timestamp="+timestamp+"&sign="+sign;
        logger.info("markdown请求钉钉接口url:{}",url);

        DingTalkClient client = new DefaultDingTalkClient(url);
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setIsAtAll(true);
        request.setAt(at);


        request.setMsgtype("markdown");
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH点mm分");
        markdown.setTitle("天气");
        markdown.setText("#### 北京天气 @183****3253\n" +
                "> 31摄氏度霾（实时），多云东风微风，空气良90，相对温度73%\n\n" +
                "> ![screenshot](https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png)\n"  +
                "> ###### "+simpleDateFormat.format(new Date())+"发布 [天气](http://www.weather.com.cn/weather/101010100.shtml/) \n");
        request.setMarkdown(markdown);
        OapiRobotSendResponse response = null;
        logger.error("获取markdown响应的结果");
        try {
            response = client.execute(request);
            if(response != null && response.isSuccess()){
                logger.info("钉钉接口调用成功:{},:{}",response.getBody(),response.getMessage());
            }
        } catch (ApiException e) {
            logger.error("调用钉钉接口出现错误:{}",e);
        }
        return JSON.toJSONString(response);
    }


    public String sendActionCardMessage(String actionCardMessage) {
        logger.info("开始调用sendActionCardMessage方法:{}",actionCardMessage);
        if(StringUtils.isEmpty(secret)){
            return "actionCard加签内容secret为空";
        }
        Long timestamp = System.currentTimeMillis();
        String sign;
        try{
            sign = SecretCrypto.crypto(secret,timestamp);
        }
        catch (Exception e){
            logger.error("actionCard签名加密出现异常:{}",e);
            return "actionCard签名加密出现异常"+e;
        }
        if(timestamp == null){
            return "actionCard时间戳timestamp为空";
        }
        if(StringUtils.isEmpty(sign)){
            return "actionCard签名sign为空";
        }

        String url2 = urlAccessToken+"&timestamp="+timestamp+"&sign="+sign;
        logger.info("actionCard请求钉钉接口url:{}",url2);

        DingTalkClient client = new DefaultDingTalkClient(url2);
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setIsAtAll(true);
        request.setAt(at);


        request.setMsgtype("actionCard");
        OapiRobotSendRequest.Actioncard actioncard = new OapiRobotSendRequest.Actioncard();
        actioncard.setBtnOrientation("0");
        actioncard.setSingleTitle("阅读全文");
        actioncard.setSingleURL("http://news.baidu.com/");
        actioncard.setTitle("高考倒计时10天");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH点mm分");
        actioncard.setText(defaultContext+"中秋快乐\n" +
                "\n" +
                "学习强国\n" +
                "\n" +
                "\n" +
                "按时打卡，做好防护\n" +
                "\n" +
                "这是一首奋斗之歌\n" +
                "\n" +
                "古城雄姿 五彩缤纷披新装\n" +
                "\n" +
                "这是一首梦想之歌\n" +
                "\n" +
                "一个都不能少 追梦圆梦在今朝\n" +
                "> 父老乡亲都在为岁月点赞\n" +
                "\n" +
                "勤劳的追梦人喜上眉梢\n" +
                "\n" +
                "一个都不能少 千里万里同怀抱\n" +
                "\n" +
                "一个都不能少 追梦圆梦在今朝\n\n" +
                "> ![screenshot](http://t.cn/ESCIlSI?m=4367047616397038&u=1873217064)\n"  +
                "> ###### "+simpleDateFormat.format(new Date())+"分发布 [你大爷](https://www.soulapp.cn/) \n");
        request.setActionCard(actioncard);
        OapiRobotSendResponse response = null;
        logger.error("获取actionCard响应的结果");
        try {
            response = client.execute(request);
            if(response != null && response.isSuccess()){
                logger.info("钉钉接口调用成功:{},:{}",response.getBody(),response.getMessage());
            }
        } catch (ApiException e) {
            logger.error("调用钉钉接口出现错误:{}",e);
        }
        return JSON.toJSONString(response);
    }
}