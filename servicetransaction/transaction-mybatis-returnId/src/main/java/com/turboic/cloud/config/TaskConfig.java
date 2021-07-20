package com.turboic.cloud.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author liebe
 */
@Configuration
@EnableScheduling
public class TaskConfig {

    public TaskConfig() {
    }

    @Scheduled(cron = "0/1 * * * * ?")
    private void execute() {
    }
}