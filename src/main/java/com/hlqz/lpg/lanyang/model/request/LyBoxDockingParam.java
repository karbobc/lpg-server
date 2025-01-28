package com.hlqz.lpg.lanyang.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Karbob
 * @date 2025-01-28
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LyBoxDockingParam {

    @JsonProperty("action")
    private String action;

    @JsonProperty("page")
    private Integer page;

    @JsonProperty("limit")
    private Integer limit;

    /**
     * JSON 格式, see {@link com.hlqz.lpg.lanyang.model.common.LyBoxDockingField}
     */
    @JsonProperty("field")
    private String field;
}
