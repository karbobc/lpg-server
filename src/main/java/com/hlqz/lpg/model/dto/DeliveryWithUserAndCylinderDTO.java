package com.hlqz.lpg.model.dto;

import com.hlqz.lpg.model.entity.Cylinder;
import com.hlqz.lpg.model.entity.Delivery;
import com.hlqz.lpg.model.entity.User;
import lombok.Data;

/**
 * @author Karbob
 * @date 2024-01-21
 */
@Data
public class DeliveryWithUserAndCylinderDTO {

    /**
     * 配送信息, 主表
     */
    private Delivery delivery;

    /**
     * 用户信息, 关联 t_user
     */
    private User user;

    /**
     * 钢瓶信息, 关联 t_cylinder
     */
    private Cylinder cylinder;
}
