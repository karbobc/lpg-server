package com.hlqz.lpg.lanyang.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Karbob
 * @date 2024-01-24
 */
@Data
@NoArgsConstructor
public class LyBaseResponse {

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("message")
    private String message;
}
