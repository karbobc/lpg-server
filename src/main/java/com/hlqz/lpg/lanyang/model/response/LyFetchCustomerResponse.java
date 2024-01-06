package com.hlqz.lpg.lanyang.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hlqz.lpg.lanyang.model.common.LyCustomer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Karbob
 * @date 2024-01-06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LyFetchCustomerResponse {

    /**
     * 客户列表
     */
    @JsonProperty("rows")
    private List<LyCustomer> crList;

    /**
     * 总数
     */
    @JsonProperty("total")
    private Integer total;
}
