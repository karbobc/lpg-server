package com.hlqz.lpg.ntfy.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Karbob
 * @date 2024-01-17
 */
@Getter
@AllArgsConstructor
public enum NtfyPriorityEnum {

    MIN_PRIORITY(1),

    LOW_PRIORITY(2),

    DEFAULT_PRIORITY(3),

    HIGH_PRIORITY(4),

    MAX_PRIORITY(5);

    @JsonValue
    private final int value;
}
