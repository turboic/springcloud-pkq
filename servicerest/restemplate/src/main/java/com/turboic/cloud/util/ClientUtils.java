package com.turboic.cloud.util;
import com.turboic.cloud.entity.User;
import com.turboic.cloud.request.RequestRestController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ClientUtils {
    private static final Log log = LogFactory.getLog(RequestRestController.class);
    private static final int CONNECT_TIME_OUT = 5000;
    private static final int CONNECT_REQ_TIME_OUT = 5000;
    private static final int SOCKET_TIME_OUT = 5000;
    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

    public static String execute(String url,String json,Map<String,String> headerParamsMap){
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(CONNECT_TIME_OUT).setConnectionRequestTimeout(CONNECT_REQ_TIME_OUT)
                .setSocketTimeout(SOCKET_TIME_OUT).build();
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("Content-type", "application/json; charset=utf-8");
        httpPost.setHeader("Accept", "application/json");
        if (null != headerParamsMap && headerParamsMap.size() > 0){
            for (Map.Entry<String,String> entry : headerParamsMap.entrySet()){
                String key = entry.getKey();
                String value = entry.getValue();
                httpPost.setHeader(key, value);
            }
        }
        String result;
        httpPost.setEntity(new StringEntity(json, Charset.forName("UTF-8")));
        try
        {
            CloseableHttpResponse res = httpClient.execute(httpPost);
            result = EntityUtils.toString(res.getEntity());
            log.info(result);
            if (res.getStatusLine().getStatusCode() != 200 && res.getStatusLine().getStatusCode() != 201){
                throw new RuntimeException("服务器内部错误500");
            }
            res.close();
        }
        catch (Exception e){
            log.error(e);
            throw new RuntimeException("服务器内部错误500",e);
        }
        finally{
            try{
                httpClient.close();
            }catch (Exception e){
                log.error(e);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String url = "http://localhost:9993/json/post";
        User user = new User();
        user.setUsername("莫醒醒");
        user.setPassword("123456");
        user.setLoginDate(new Date());
        String json = FastJsonUtils.objectToJson(user);
        Map<String,String> headerParamsMap = new HashMap<>();
        headerParamsMap.put("app","抖音");
        headerParamsMap.put("address","北京是朝阳区北土城西路");
        String result = ClientUtils.execute(url,json,headerParamsMap);
        System.err.println(result);
    }
}
