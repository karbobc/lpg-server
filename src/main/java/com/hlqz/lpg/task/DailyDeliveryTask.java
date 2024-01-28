package com.hlqz.lpg.task;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hlqz.lpg.lanyang.model.common.LyCustomer;
import com.hlqz.lpg.lanyang.model.dto.LyDeliveryDTO;
import com.hlqz.lpg.lanyang.model.dto.LySaveCustomerDTO;
import com.hlqz.lpg.lanyang.service.LyService;
import com.hlqz.lpg.model.dto.DeliveryWithUserAndCylinderDTO;
import com.hlqz.lpg.model.entity.Cylinder;
import com.hlqz.lpg.model.entity.User;
import com.hlqz.lpg.model.enums.DeliveryStateEnum;
import com.hlqz.lpg.mybatis.dao.DeliveryDAO;
import com.hlqz.lpg.util.AssertionUtils;
import com.hlqz.lpg.util.DateTimeUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Karbob
 * @date 2024-01-17
 */
@Slf4j
@Component
public class DailyDeliveryTask {

    /**
     * 每次配送的批次大小
     */
    private static final int BATCH_SIZE = 20;

    @Resource
    private DeliveryDAO deliveryDAO;
    @Resource
    private LyService lyService;

    private DailyDeliveryTask() {
    }

    @Scheduled(cron = "0 0 */2 * * ?")
    private void scheduler() {
        final var df = DateTimeFormatter.ofPattern(DateTimeUtils.PATTERN_DATETIME);
        log.info("DailyDeliveryTask, 执行配送任务开始, datetime: {}", LocalDateTime.now().format(df));
        try {
            execute();
        } catch (Exception e) {
            log.error("DailyDeliveryTask, 执行配送任务异常", e);
        }
        log.info("DailyDeliveryTask, 执行配送定时任务结束");
    }

    private void execute() {
        // 查询未配送和配送失败的配送信息, 调用兰洋系统进行配送
        IPage<DeliveryWithUserAndCylinderDTO> page = new Page<>(1, BATCH_SIZE);
        page = deliveryDAO.fetchPageByStates(page, DeliveryStateEnum.NOT_STARTED);
        final var pages = page.getPages();
        for (var i = 1; i <= pages; i++) {
            if (i != 1) {
                page.setCurrent(i);
                page = deliveryDAO.fetchPageByStates(page, DeliveryStateEnum.NOT_STARTED);
            }
            final var records = page.getRecords();
            if (CollectionUtils.isEmpty(records)) {
                break;
            }
            for (var data : records) {
                try {
                    delivery(data);
                } catch (Exception e) {
                    log.error("DailyDeliveryTask, 配送异常, data: {}", data, e);
                }
            }
        }
    }

    private void delivery(DeliveryWithUserAndCylinderDTO dto) {
        // 根据数据库中用户数据, 查询兰洋系统中对应客户, 如果没有查询到, 需要在兰洋系统中新增
        final var delivery = dto.getDelivery();
        final var user = dto.getUser();
        final var cylinder = dto.getCylinder();
        final var lyCustomerList = lyService.fetchCustomerByNameAndMobile(user.getRealName(), user.getMobile());
        if (lyCustomerList.size() > 1) {
            // 存在多个客户, 人工处理
            log.warn("DailyDeliveryTask, 该用户信息在兰洋系统中存在多个, name: {}, mobile: {}", user.getRealName(), user.getMobile());
            delivery.setState(DeliveryStateEnum.CRASH);
            AssertionUtils.assertTrue(deliveryDAO.updateById(delivery), "更新配送状态失败");
            return;
        }
        // 如果兰洋系统不存在该客户, 先新增一个客户, 再进行配送
        final var lyCustomer = CollectionUtils.isEmpty(lyCustomerList) ?
            lyService.saveCustomer(buildLySaveCustomerDTO(user, lyService.fetchAvailableCrNo())) : lyCustomerList.getFirst();
        final var lyDeliveryDTO = buildLyDeliveryDTO(lyCustomer, cylinder);
        lyService.delivery(lyDeliveryDTO);
        // 更新系统配送状态
        delivery.setState(DeliveryStateEnum.DONE);
        AssertionUtils.assertTrue(deliveryDAO.updateById(delivery), "更新配送状态失败");
    }

    private LySaveCustomerDTO buildLySaveCustomerDTO(User user, Integer crNo) {
        final var dto = new LySaveCustomerDTO();
        dto.setCrNo(crNo);
        dto.setName(user.getRealName());
        dto.setMobile(user.getMobile());
        dto.setAddress(user.getAddress());
        return dto;
    }

    private LyDeliveryDTO buildLyDeliveryDTO(LyCustomer lyCustomer, Cylinder cylinder) {
        final var dto = new LyDeliveryDTO();
        dto.setDeliveryDate(LocalDate.now());
        dto.setBarcodeList(List.of(cylinder.getBarcode()));
        dto.setCustomer(lyCustomer);
        return dto;
    }
}
