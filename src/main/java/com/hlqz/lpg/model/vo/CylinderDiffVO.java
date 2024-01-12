package com.hlqz.lpg.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Karbob
 * @date 2024-01-12
 */
@Data
@NoArgsConstructor
public class CylinderDiffVO {

    /**
     * 钢印号 1
     */
    @JsonProperty("serialNo1")
    private String serialNo1;

    /**
     * 钢印号 2
     */
    @JsonProperty("serialNo2")
    private String serialNo2;

    /**
     * 气瓶条码 1
     */
    @JsonProperty("barcode1")
    private String barcode1;

    /**
     * 气瓶条码 2
     */
    @JsonProperty("barcode2")
    private String barcode2;
}
