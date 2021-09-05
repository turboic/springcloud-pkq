package com.turboic.cloud.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.io.File;

/**
 * Unit test for simple App.
 *
 * @author liebe
 */
@Configuration
@EnableScheduling
public class MavenTest implements SchedulingConfigurer {

    @Value("${maven.path}")
    private String mavenPath;

    public void clearMavenLastUpdateFile(String path) {
        if (path == null) {
            return;
        }

        File directory = new File(path);

        if (!directory.isDirectory()) {
            return;
        }

        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith("lastUpdated")) {
                System.err.println(file.getAbsolutePath());
                file.delete();
            } else {
                clearMavenLastUpdateFile(file.getAbsolutePath());
            }
        }
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(
                () -> clearMavenLastUpdateFile(mavenPath),
                triggerContext -> new CronTrigger("*/30 * * * * *")
                        .nextExecutionTime(triggerContext));
    }
}
