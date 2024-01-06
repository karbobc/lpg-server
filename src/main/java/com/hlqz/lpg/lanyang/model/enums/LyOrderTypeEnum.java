package com.hlqz.lpg.lanyang.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Karbob
 * @date 2024-01-06
 */
@Getter
@AllArgsConstructor
public enum LyOrderTypeEnum {

    /**
     * 升序
     */
    ASC("asc"),

    /**
     * 降序
     */
    DESC("desc");

    @JsonValue
    private final String value;
}
