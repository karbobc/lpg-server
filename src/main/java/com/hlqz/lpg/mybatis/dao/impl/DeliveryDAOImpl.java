package com.hlqz.lpg.mybatis.dao.impl;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.toolkit.JoinWrappers;
import com.hlqz.lpg.model.entity.Cylinder;
import com.hlqz.lpg.model.entity.Delivery;
import com.hlqz.lpg.model.enums.DeliveryStateEnum;
import com.hlqz.lpg.mybatis.dao.DeliveryDAO;
import com.hlqz.lpg.mybatis.mapper.DeliveryMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author Karbob
 * @date 2024-01-15
 */
@Service
public class DeliveryDAOImpl extends MPJBaseServiceImpl<DeliveryMapper, Delivery> implements DeliveryDAO {

    @Override
    public boolean existsByUserIdAndBarcodeAndState(Long userId, String barcode, DeliveryStateEnum... state) {
        return JoinWrappers.lambda(Delivery.class)
            .leftJoin(Cylinder.class, Cylinder::getId, Delivery::getCylinderId)
            .eq(Delivery::getUserId, userId)
            .eq(Cylinder::getBarcode, barcode)
            .in(Delivery::getState, Arrays.asList(state))
            .count() > 0;
    }
}
