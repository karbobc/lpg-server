package com.hlqz.lpg.lanyang.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Karbob
 * @date 2024-01-07
 *
 * 分页响应结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LyPageResponse<T> {

    /**
     * 分页数据列表
     */
    @JsonProperty("rows")
    private List<T> data;

    /**
     * 总数
     */
    @JsonProperty("total")
    private Integer count;
}
