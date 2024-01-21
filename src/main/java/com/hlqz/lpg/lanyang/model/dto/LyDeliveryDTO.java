package com.hlqz.lpg.lanyang.model.dto;

import com.hlqz.lpg.lanyang.model.common.LyCustomer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Karbob
 * @date 2024-01-21
 */
@Data
@NoArgsConstructor
public class LyDeliveryDTO {

    /**
     * 兰洋系统客户信息
     */
    private LyCustomer customer;

    /**
     * 配送的气瓶条码
     */
    private List<String> barcodeList;

    /**
     * 配送日期
     */
    private LocalDate deliveryDate;
}
