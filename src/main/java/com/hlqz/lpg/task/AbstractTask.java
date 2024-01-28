package com.hlqz.lpg.task;

import cn.hutool.core.util.IdUtil;
import com.hlqz.lpg.lanyang.service.LyService;
import com.hlqz.lpg.mybatis.dao.DeliveryDAO;
import com.hlqz.lpg.util.DateTimeUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Karbob
 * @date 2024-01-28
 */
@Slf4j
@Component
public abstract class AbstractTask {

    private static final DateTimeFormatter DEFAULT_DF = DateTimeFormatter.ofPattern(DateTimeUtils.PATTERN_DATETIME);

    private static ExecutorService executor = null;

    @Resource
    public LyService lyService;
    @Resource
    public DeliveryDAO deliveryDAO;

    public ExecutorService getExecutor() {
        if (Objects.isNull(executor)) {
            synchronized (this) {
                if (Objects.isNull(executor)) {
                    executor = new ThreadPoolExecutor(
                        4, 8, 1, TimeUnit.SECONDS,
                        new LinkedBlockingDeque<>(4),
                        (r, e) -> {
                            log.error("AbstractTask, 线程池已满");
                        }
                    );
                }
            }
        }
        return executor;
    }

    public void scheduler() {
        getExecutor().execute(() -> {
            final var traceId = IdUtil.fastSimpleUUID();
            MDC.put("traceId", traceId);
            log.info("定时任务开始执行, datetime: {}", LocalDateTime.now().format(DEFAULT_DF));
            try {
                execute();
            } catch (Exception e) {
                log.error("定时任务执行异常", e);
            } finally {
                log.info("定时任务执行结束");
                MDC.remove("traceId");
            }
        });
    }

    public abstract void execute();
}
