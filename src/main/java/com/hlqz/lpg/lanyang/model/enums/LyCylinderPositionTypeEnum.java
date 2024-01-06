package com.hlqz.lpg.lanyang.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Karbob
 * @date 2024-01-07
 */
@Getter
@AllArgsConstructor
public enum LyCylinderPositionTypeEnum {

    /**
     * 钢瓶仍在库
     */
    IN_STOCK("在库"),

    /**
     * 钢瓶流转给客户
     */
    CUSTOMER("客户");

    @JsonValue
    private final String value;
}
