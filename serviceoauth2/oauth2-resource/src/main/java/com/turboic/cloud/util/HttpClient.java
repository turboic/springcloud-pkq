package com.turboic.cloud.util;
import okhttp3.*;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * @author import
 */
public class HttpClient {

    /**
     * 原始OkHttpClient，全局保持唯一一个，从而保证性能开销
     */
    private OkHttpClient mOkHttpClient = null;

    /**
     * httpClient的配置选项 {@link HttpClientConfig}
     */
    private HttpClientConfig config;

    /**
     * 构造方法
     */
    public HttpClient() {
        this(new HttpClientConfig());
    }

    /**
     * 构造方法
     *
     * @param config {@link HttpClientConfig}
     */
    public HttpClient(HttpClientConfig config) {
        if (config == null) {
            config = new HttpClientConfig();
        }
        this.setConfig(config);
    }

    public void setConfig(HttpClientConfig config) {
        if (config == null) {
            return;
        }
        this.config = config;
        resetHttpClient();
    }

    public HttpClientConfig getConfig() {
        return config;
    }

    /**
     * 重新配置HttpClient
     *
     */
    public synchronized void resetHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                //设置超时连接时间
                .connectTimeout(config.getTimeoutConnect(), TimeUnit.MILLISECONDS)
                //设置写入超时时间
                .writeTimeout(config.getTimeoutWrite(), TimeUnit.MILLISECONDS)
                //设置读取超市时间
                .readTimeout(config.getTimeoutRead(), TimeUnit.MILLISECONDS)
                //连接池
                .connectionPool(new ConnectionPool(
                        config.getMaxIdleConnections(),
                        config.getKeepAliveDuration(),
                        config.getKeepAliveTimeUnit()));
        if (config.getInterceptors() != null) {
            for (Interceptor interceptor : config.getInterceptors()) {
                if (interceptor == null) {
                    continue;
                }
                builder.addInterceptor(interceptor);
            }
        }
        if (config.getNetworkInterceptors() != null) {
            for (Interceptor interceptor : config.getNetworkInterceptors()) {
                if (interceptor == null) {
                    continue;
                }
                builder.addNetworkInterceptor(interceptor);
            }
        }
        if (config.getExecutorService() != null) {
            builder.dispatcher(new Dispatcher(config.getExecutorService()));
        }


        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };
        //支持HTTPS请求，跳过证书验证
        builder.sslSocketFactory(createSSLSocketFactory(trustAllCerts), (X509TrustManager) trustAllCerts[0]);
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });
        mOkHttpClient = builder.build();
    }

    /**
     * 生成安全套接字工厂，用于https请求的证书跳过
     *
     * @return SSLSocketFactory
     */
    private SSLSocketFactory createSSLSocketFactory(TrustManager[] trustManagers) {
        SSLSocketFactory ssfFactory = null;
        try {
            //"SSLv2Hello", "SSLv3", "TLSv1"
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, trustManagers, new SecureRandom());
            ssfFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
        }
        return ssfFactory;
    }


    public synchronized OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public synchronized Call newCall(Request request){
        return mOkHttpClient.newCall(request);
    }
}