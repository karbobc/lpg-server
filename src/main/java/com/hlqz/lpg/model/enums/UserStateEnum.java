package com.hlqz.lpg.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Karbob
 * @date 2024-01-05
 */
@Getter
@AllArgsConstructor
public enum UserStateEnum {

    /**
     * 注销
     */
    DELETED(-1),

    /**
     * 正常
     */
    OK(0);

    @JsonValue
    @EnumValue
    private final int value;
}
