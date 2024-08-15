package com.hlqz.lpg.lanyang.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Karbob
 * @date 2024-08-15
 */
@Getter
@AllArgsConstructor
public enum LyCylinderUploadStateEnum {

    /**
     * 已上传
     */
    UPLOADED("1"),

    /**
     * 上传失败
     */
    FAIL("2");

    @JsonValue
    private final String value;
}
