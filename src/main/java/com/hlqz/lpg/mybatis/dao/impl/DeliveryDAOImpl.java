package com.hlqz.lpg.mybatis.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.toolkit.JoinWrappers;
import com.hlqz.lpg.model.dto.DeliveryWithUserAndCylinderDTO;
import com.hlqz.lpg.model.entity.Cylinder;
import com.hlqz.lpg.model.entity.Delivery;
import com.hlqz.lpg.model.entity.User;
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
    public boolean existsByUserIdAndBarcodeAndStates(Long userId, String barcode, DeliveryStateEnum... states) {
        return JoinWrappers.lambda(Delivery.class)
            .leftJoin(Cylinder.class, Cylinder::getId, Delivery::getCylinderId)
            .eq(Delivery::getUserId, userId)
            .eq(Cylinder::getBarcode, barcode)
            .in(Delivery::getState, Arrays.asList(states))
            .count() > 0;
    }

    @Override
    public IPage<DeliveryWithUserAndCylinderDTO> fetchPageByStates(IPage<DeliveryWithUserAndCylinderDTO> page,
                                                                   DeliveryStateEnum... states) {
        return JoinWrappers.lambda(Delivery.class)
            .selectAssociation(Delivery.class, DeliveryWithUserAndCylinderDTO::getDelivery)
            .selectAssociation(User.class, DeliveryWithUserAndCylinderDTO::getUser)
            .selectAssociation(Cylinder.class, DeliveryWithUserAndCylinderDTO::getCylinder)
            .leftJoin(User.class, User::getId, Delivery::getUserId)
            .leftJoin(Cylinder.class, Cylinder::getId, Delivery::getCylinderId)
            .in(Delivery::getState, Arrays.asList(states))
            .page(page, DeliveryWithUserAndCylinderDTO.class);
    }
}
