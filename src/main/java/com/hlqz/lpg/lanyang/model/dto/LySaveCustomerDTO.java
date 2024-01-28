package com.hlqz.lpg.lanyang.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Karbob
 * @date 2024-01-24
 */
@Data
@NoArgsConstructor
public class LySaveCustomerDTO {

    /**
     * 客户号, 2-8位数字
     */
    private Integer crNo;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 住址
     */
    private String address;
}
