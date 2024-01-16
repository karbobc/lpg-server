package com.hlqz.lpg.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Karbob
 * @date 2024-01-09
 */
@Data
@NoArgsConstructor
public class EnrollDTO {

    /**
     * 用户真实姓名
     */
    private String realName;

    /**
     * 用户手机号
     */
    private String mobile;

    /**
     * 用户地址
     */
    private String address;

    /**
     * 气瓶条码
     */
    private String barcode;
}
