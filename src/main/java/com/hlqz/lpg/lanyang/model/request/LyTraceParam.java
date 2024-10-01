package com.hlqz.lpg.lanyang.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Karbob
 * @date 2024-10-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LyTraceParam {

    /**
     * 钢瓶 ID
     */
    @JsonProperty("DOCID")
    private String cylinderId;

    @JsonProperty("LZ")
    private Integer lz;
}
