package com.turboic.cloud.fallback;

import com.turboic.cloud.openfeign.OpenFeignService;

public class OpenFeignFallbackService implements OpenFeignService {
    private static final String DEFAULT_DESC = "调用微服务sentinel-nacos-provider程序异常，";
    private static final String DEFAULT_BROKER = "进行服务熔断降级处理,传入参数:";
    private static final String DEFAULT_EX_DESC = ",异常信息:";

    private Throwable throwable;
    public OpenFeignFallbackService(Throwable throwable) {
        this.throwable = throwable;
    }
    @Override
    public String cloud(String param) {
        return DEFAULT_DESC + OpenFeignFallbackService.class.getName() +
                DEFAULT_BROKER +param + DEFAULT_EX_DESC + throwable.getMessage();
    }
}