package com.hlqz.lpg.model.convert;

import com.hlqz.lpg.model.entity.Cylinder;
import com.hlqz.lpg.model.entity.Delivery;
import com.hlqz.lpg.model.entity.User;

/**
 * @author Karbob
 * @date 2024-09-22
 */
public class DeliveryConvert {

    public static Delivery from(User user, Cylinder cylinder) {
        final var delivery = new Delivery();
        delivery.setUserId(user.getId());
        delivery.setCylinderId(cylinder.getId());
        return delivery;
    }
}
