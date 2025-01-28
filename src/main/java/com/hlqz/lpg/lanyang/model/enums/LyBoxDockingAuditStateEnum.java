package com.hlqz.lpg.lanyang.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Karbob
 * @date 2025-01-28
 */
@Getter
@AllArgsConstructor
public enum LyBoxDockingAuditStateEnum {

    PASS(1, "审核通过"),

    REJECT(0, "审核未通过"),

    PENDING(-1, "待审核");

    @JsonValue
    private final int value;

    private final String desc;
}
