package com.hlqz.lpg.lanyang.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hlqz.lpg.lanyang.model.enums.LyOrderTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Karbob
 * @date 2024-01-06
 *
 * 分页查询数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LyFetchByPageParam {

    /**
     * 页码
     */
    @JsonProperty("page")
    private Integer page;

    /**
     * 页码大小
     */
    @JsonProperty("rows")
    private Integer size;

    /**
     * SQL 的 Where 条件, 需要经过 base64 加密
     */
    @JsonProperty("sqlWhere")
    private String sqlWhere;
    /**
     * 排序字段
     */
    @JsonProperty("sort")
    private String orderBy;

    /**
     * 排序类型, desc asc
     */
    @JsonProperty("order")
    private LyOrderTypeEnum orderType;
}
