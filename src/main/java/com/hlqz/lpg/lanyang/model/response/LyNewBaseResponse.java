package com.hlqz.lpg.lanyang.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Karbob
 * @date 2025-01-28
 */
@Data
@NoArgsConstructor
public class LyNewBaseResponse<T> {

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("msg")
    private String message;

    @JsonProperty("data")
    private T data;
}
