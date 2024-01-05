package com.hlqz.lpg.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Karbob
 * @date 2024-01-06
 */
@Getter
@AllArgsConstructor
public enum DeliveryStateEnum {

    /**
     * 未配送
     */
    NOT_STARTED(0),

    /**
     * 已配送
     */
    DONE(1);

    @JsonValue
    @EnumValue
    private final int value;
}
