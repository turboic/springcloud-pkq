package com.turboic.cloud.util;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author import
 */
public class LogInterceptor implements Interceptor {
    private static Logger log = LoggerFactory.getLogger(LogInterceptor.class);
    private String TAG = getClass().getSimpleName();


    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        //获得请求信息，此处如有需要可以添加headers信息
        Request request = chain.request();
        log.trace(TAG, "[request]:" + request.toString());
        log.trace(TAG, "[request-headers]:" + request.headers().toString());
        /* 记录请求耗时 */
        long startNs = System.nanoTime();
        /* 发送请求，获得响应 */
        Response response = chain.proceed(request);

        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        /* 打印请求耗时 */
        log.debug(TAG, "[耗时]:" + tookMs + "ms");
        /* 使用response获得headers(),可以更新本地Cookie。*/
        log.trace(TAG, "[response-code]:" + response.code());
        log.trace(TAG, "[response-headers]:" + response.headers().toString());
        return response;
    }

    private String readRequestBody(Request oriReq) {
        if (oriReq.body() == null){
            return "";
        }
        Request request = oriReq.newBuilder().build();
        Buffer buffer = new Buffer();
        try {
            request.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}