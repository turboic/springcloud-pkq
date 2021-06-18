package com.turboic.cloud.config;

import com.turboic.cloud.entity.Liebe;
import com.turboic.cloud.service.LiebeService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class TaskConfig {


    private final LiebeService liebeService;

    public TaskConfig(LiebeService liebeService) {
        this.liebeService = liebeService;
    }

    @Scheduled(cron = "0/1 * * * * ?")
    private void execute() {
        liebeService.processing(new Liebe());
    }
}