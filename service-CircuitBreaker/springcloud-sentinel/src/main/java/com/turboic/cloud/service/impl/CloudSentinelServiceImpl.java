package com.turboic.cloud.service.impl;

import com.turboic.cloud.openfeign.OpenFeignService;
import com.turboic.cloud.service.CloudSentinelService;
import org.springframework.stereotype.Service;

@Service
public class CloudSentinelServiceImpl implements CloudSentinelService {

    private final OpenFeignService openFeignService;

    public CloudSentinelServiceImpl(OpenFeignService openFeignService) {
        this.openFeignService = openFeignService;
    }

    @Override
    public String cloud(String name) {
        return openFeignService.cloud(name);
    }
}
