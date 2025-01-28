package com.hlqz.lpg.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Karbob
 * @date 2024-08-14
 */
@Data
@NoArgsConstructor
public class LyBoxDockingDTO {

    /**
     * 气瓶条码
     */
    private String barcode;

    /**
     * 是否获取溯源客户信息
     */
    private Boolean trace;
}
