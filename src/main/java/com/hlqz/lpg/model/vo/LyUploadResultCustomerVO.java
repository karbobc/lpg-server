package com.hlqz.lpg.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Karbob
 * @date 2024-10-01
 */
@Data
@NoArgsConstructor
public class LyUploadResultCustomerVO {

    /**
     * 客户姓名
     */
    @JsonProperty("name")
    private String name;

    /**
     * 客户电话
     */
    @JsonProperty("mobile")
    private String mobile;

    /**
     * 客户详细地址
     */
    @JsonProperty("address")
    private String address;
}
