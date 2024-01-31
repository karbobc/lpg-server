package com.hlqz.lpg.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * @author Karbob
 * @date 2024-01-31
 */
@Slf4j
@Configuration
public class SchedulingConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(@NonNull ScheduledTaskRegistrar registrar) {
        final var executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(8);
        executor.setThreadNamePrefix("scheduling-pool-");
        executor.setRejectedExecutionHandler((r, e) -> {
            log.error("Scheduling, 线程池已满");
        });
        executor.initialize();
        registrar.setScheduler(executor);
    }
}
