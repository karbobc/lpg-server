package com.hlqz.lpg.mybatis.dao;

import com.github.yulichang.base.MPJBaseService;
import com.hlqz.lpg.model.entity.Delivery;
import com.hlqz.lpg.model.enums.DeliveryStateEnum;

/**
 * @author Karbob
 * @date 2024-01-15
 */
public interface DeliveryDAO extends MPJBaseService<Delivery> {

    /**
     * 根据用户 ID, 气瓶条码, 配送状态判断配送信息是否存在
     * @param userId 用户 ID
     * @param barcode 气瓶条码
     * @param state 配送状态
     */
    boolean existsByUserIdAndBarcodeAndState(Long userId, String barcode, DeliveryStateEnum... state);
}
