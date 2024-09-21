package com.hlqz.lpg.model.convert;

import com.hlqz.lpg.model.dto.EnrollDTO;
import com.hlqz.lpg.model.entity.User;

/**
 * @author Karbob
 * @date 2024-09-22
 */
public class UserConvert {

    public static User from(EnrollDTO dto) {
        final var user = new User();
        user.setRealName(dto.getRealName());
        user.setMobile(dto.getMobile());
        user.setAddress(dto.getAddress());
        return user;
    }
}
