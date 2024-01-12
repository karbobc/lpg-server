package com.hlqz.lpg.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Karbob
 * @date 2024-01-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CylinderSearchVO {

    /**
     * 条码
     */
    @JsonProperty("barcode")
    public String barcode;
}
