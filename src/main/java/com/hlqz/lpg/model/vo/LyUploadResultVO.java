package com.hlqz.lpg.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Karbob
 * @date 2024-08-14
 */
@Data
@NoArgsConstructor
public class LyUploadResultVO {

    /**
     * 气瓶条码
     */
    @JsonProperty("barcode")
    private String barcode;

    /**
     * 钢印号
     */
    @JsonProperty("serialNo")
    private String serialNo;

    /**
     * 上传结果
     */
    @JsonProperty("result")
    private String result;
}
