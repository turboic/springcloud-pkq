package com.turboic.cloud.util;
import okhttp3.Interceptor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
/**
 * @author import
 */
public class HttpClientConfig {
    public static final long DEFAULT_CONNECT_TIMEOUT = 10 * 1000;
    public static final long DEFAULT_WRITE_TIMEOUT = 30 * 1000;
    public static final long DEFAULT_READ_TIMEOUT = 30 * 1000;
    /**
     * 默认okhttp最大空闲连接数（5）
     */
    public static final int DEFAULT_MAX_IDLE_CONNECTIONS = 5;
    /**
     * 默认okhttp活动链接存货时间（5分钟）
     */
    public static final long DEFAULT_KEEP_ALIVE_DURATION_MINUTES = 5;
    /**
     * 默认okhttp活动链接存货时间单位, （分钟）
     */
    public static final TimeUnit DEFAULT_KEEP_ALIVE_DURATION_TIME_UNIT = TimeUnit.MINUTES;

    private long timeoutConnect;
    private long timeoutRead;
    private long timeoutWrite;
    private int maxIdleConnections;
    private long keepAliveDuration;
    private TimeUnit keepAliveTimeUnit;
    private List<Interceptor> interceptors;
    private List<Interceptor> networkInterceptors;
    private ExecutorService executorService;

    public HttpClientConfig() {
        this(DEFAULT_MAX_IDLE_CONNECTIONS, DEFAULT_KEEP_ALIVE_DURATION_MINUTES, DEFAULT_KEEP_ALIVE_DURATION_TIME_UNIT);
    }

    public HttpClientConfig(int maxIdleConnections, long keepAliveDuration, TimeUnit keepAliveTimeUnit) {
        this.maxIdleConnections = maxIdleConnections;
        this.keepAliveDuration = keepAliveDuration;
        this.keepAliveTimeUnit = keepAliveTimeUnit;
        this.timeoutConnect = DEFAULT_CONNECT_TIMEOUT;
        this.timeoutRead = DEFAULT_READ_TIMEOUT;
        this.timeoutWrite = DEFAULT_WRITE_TIMEOUT;
        this.interceptors = new ArrayList<>();
        this.interceptors.add(new LogInterceptor());
    }

    public HttpClientConfig setInterceptors(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
        return this;
    }

    public HttpClientConfig addInterceptor(Interceptor interceptor) {
        if (interceptor == null){
            return this;
        }

        if (this.interceptors == null){
            this.interceptors = new ArrayList<>();
        }

        this.interceptors.add(interceptor);
        return this;
    }

    public HttpClientConfig setNetworkInterceptors(List<Interceptor> networkInterceptors) {
        this.networkInterceptors = networkInterceptors;
        return this;
    }

    public HttpClientConfig addNetInterceptor(Interceptor interceptor) {
        if (interceptor == null){
            return this;
        }

        if (this.networkInterceptors == null) {
            this.networkInterceptors = new ArrayList<>();
        }
        this.networkInterceptors.add(interceptor);
        return this;
    }

    public HttpClientConfig setMaxIdleConnections(int maxIdleConnections) {
        this.maxIdleConnections = maxIdleConnections;
        return this;
    }

    public HttpClientConfig setKeepAliveDuration(long keepAliveDuration) {
        this.keepAliveDuration = keepAliveDuration;
        return this;
    }

    public HttpClientConfig setKeepAliveTimeUnit(TimeUnit keepAliveTimeUnit) {
        this.keepAliveTimeUnit = keepAliveTimeUnit;
        return this;
    }

    public HttpClientConfig setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
        return this;
    }

    public HttpClientConfig setTimeout(long timeoutConnect, long timeoutRead, long timeoutWrite) {
        this.timeoutConnect = timeoutConnect;
        this.timeoutRead = timeoutRead;
        this.timeoutWrite = timeoutWrite;
        return this;
    }

    public long getTimeoutConnect() {
        return timeoutConnect;
    }

    public long getTimeoutRead() {
        return timeoutRead;
    }

    public long getTimeoutWrite() {
        return timeoutWrite;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public int getMaxIdleConnections() {
        return maxIdleConnections;
    }

    public long getKeepAliveDuration() {
        return keepAliveDuration;
    }

    public TimeUnit getKeepAliveTimeUnit() {
        return keepAliveTimeUnit;
    }

    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    public List<Interceptor> getNetworkInterceptors() {
        return networkInterceptors;
    }
}