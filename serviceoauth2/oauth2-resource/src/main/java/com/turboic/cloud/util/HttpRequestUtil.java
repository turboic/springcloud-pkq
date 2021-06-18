package com.turboic.cloud.util;
import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;
/**
 * @author import
 */
public class HttpRequestUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);

    private static String ENCODING = "UTF-8";
    private HttpClient httpClient = new HttpClient();
    public final static HttpRequestUtil INSTANCE;
    private static final String CONTENT_TYPE = "Content-Type";

    static {
        INSTANCE = new HttpRequestUtil();
    }

    private HttpRequestUtil()
    {
    }
    public static HttpRequestUtil getInstance()
    {
        return INSTANCE;
    }
    /**
     * Set URI
     * @param url
     * @param params
     */
    private HttpUrl getHttpUrl(String url,
                               Map<String, String> params) {
        HttpUrl.Builder newBuilder = HttpUrl.parse(url).newBuilder();
        if (MapUtils.isNotEmpty(params)) {
            // Set params
            for (Map.Entry<String, String> stringStringEntry : params.entrySet()) {
                newBuilder.addQueryParameter(stringStringEntry.getKey(), stringStringEntry.getValue());
            }
        }
        return newBuilder.build();
    }
    /**
     * Set Header
     * @param headers 请求头数据
     */
    private Headers getHeaders(Map<String, String> headers){
        if(MapUtils.isNotEmpty(headers)){
            return Headers.of(headers);
        }
        return  new Headers.Builder().build();
    }
    /**
     * Content-Type: application/x-www-from-urlencoded
     *
     * Description: 封装请求参数
     * @param params 参数
     *
     */
    private FormBody getFormBody(Map<String, String> params) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder(Charset.forName(ENCODING));
        // 封装请求参数
        if (MapUtils.isNotEmpty(params)) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                formBodyBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        return  formBodyBuilder.build();
    }
    /**
     * Content-Type: multipart/form-data
     *
     * Description: 封装请求参数
     * @return
     */
    private MultipartBody getMultipartBody(Map<String, Object> params){
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        return multipartBodyBuilder.build();
    }
    /**
     *
     *
     * @param content
     * @param type
     * @return
     */
    private RequestBody getRawBody(String content,MediaType type){
        return RequestBody.create(content,type);
    }
    /**
     * Content-Type: application/json
     *
     * @param json
     * @return
     */
    private RequestBody getJSONBody(Object json){
        return getRawBody(JSON.toJSONString(json),MediaType.parse(ContentType.JSON.toString()));
    }

    /**
     *
     * @param url
     * @return
     * @throws IOException
     */
    public Response get(String url) throws IOException {
        return get(url,null,null);
    }
    /**
     *
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    public Response get(String url,Map<String, String> params) throws IOException {
        return get(url,null,params);
    }
    /**
     * get请求，同步方式，获取网络数据，是在主线程中执行的，需要新起线程，将其放到子线程中执行
     *
     * @param url
     * @param headers
     * @param params
     * @return
     * @throws IOException
     */
    public Response get(String url,
                        Map<String, String> headers,
                        Map<String, String> params) throws IOException {
        Request.Builder builder = new Request.Builder()
                .url(getHttpUrl(url,params))
                .headers(getHeaders(headers))
                .get();
        return httpClient
                .newCall(
                        builder
                                .build())
                .execute();
    }
    /**
     *
     *
     * @param url
     * @param headers
     * @param params
     * @param formData
     * @return
     * @throws IOException
     */
    public Response post(String url,
                         Map<String, String> headers,
                         Map<String, String> params,
                         Map<String, String> formData) throws IOException {
        headers.put(CONTENT_TYPE, ContentType.FORM.toString());
        return post(url,headers,params,getFormBody(formData));
    }
    /**
     *
     * @param url
     * @param headers
     * @param params
     * @param multipartData
     * @return
     * @throws IOException
     */
    public Response postMultipart(String url,
                                  Map<String, String> headers,
                                  Map<String, String> params,
                                  Map<String, Object> multipartData) throws IOException {
        headers.put(CONTENT_TYPE, ContentType.MULTIPART.toString());
        return post(url,headers,params,getMultipartBody(multipartData));
    }
    /**
     *
     * @param url
     * @param headers
     * @param params
     * @param json
     * @return
     * @throws IOException
     */
    public Response postJSON(String url,
                             Map<String, String> headers,
                             Map<String, String> params,
                             Object json) throws IOException {
        headers.put(CONTENT_TYPE, ContentType.JSON.toString());
        return post(url,headers,params,getJSONBody(json));
    }
    /**
     * post form表单 请求，同步方式，提交数据，是在主线程中执行的，需要新起线程，将其放到子线程中执行
     *
     * @param url
     * @param headers
     * @param params
     * @param requestBody
     * @return
     * @throws IOException
     */
    private Response post(String url,
                          Map<String, String> headers,
                          Map<String, String> params,
                          RequestBody requestBody) throws IOException {
        Request.Builder builder = new Request.Builder()
                .url(getHttpUrl(url,params))
                .headers(getHeaders(headers))
                .post(requestBody);
        return httpClient.newCall(builder.get().build()).execute();
    }
    /**
     *
     * @param url
     * @param headers
     * @param params
     * @return
     * @throws IOException
     */
    public Response head(String url,
                         Map<String, String> headers,
                         Map<String, String> params) throws IOException {
        Request.Builder builder = new Request.Builder()
                .url(getHttpUrl(url,params))
                .headers(getHeaders(headers))
                .get();
        return httpClient.newCall(builder.build()).execute();
    }
    public Response put(String url,
                        Map<String, String> headers,
                        Map<String, String> params,
                        Map<String, String> formData) throws IOException {
        Request.Builder builder = new Request.Builder()
                .url(getHttpUrl(url,params))
                .headers(getHeaders(headers))
                .put(null);
        return httpClient.newCall(builder.build()).execute();
    }
    public Response patch(String url,
                          Map<String, String> headers,
                          Map<String, String> params,
                          Map<String, String> formData) throws IOException {
        Request.Builder builder = new Request.Builder()
                .url(getHttpUrl(url,params))
                .headers(getHeaders(headers))
                .patch(null);
        return httpClient.newCall(builder.build()).execute();
    }
    public Response delete(String url,
                           Map<String, String> headers,
                           Map<String, String> params,
                           Map<String, String> formData) throws IOException {
        Request.Builder builder = new Request.Builder()
                .url(getHttpUrl(url,params))
                .headers(getHeaders(headers))
                .delete();
        return httpClient.newCall(builder.build()).execute();
    }
}
