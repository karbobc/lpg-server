package com.hlqz.lpg.task;

import com.hlqz.lpg.lanyang.service.LyService;
import com.hlqz.lpg.mybatis.dao.DeliveryDAO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author Karbob
 * @date 2024-01-28
 */
@Component
public abstract class AbstractTask {

    @Resource
    protected LyService lyService;
    @Resource
    protected DeliveryDAO deliveryDAO;

    /**
     * 定时任务入口
     */
    protected abstract void execute();
}
