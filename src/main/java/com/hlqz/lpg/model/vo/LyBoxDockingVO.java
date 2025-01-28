package com.hlqz.lpg.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Karbob
 * @date 2024-08-14
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LyBoxDockingVO {

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
     * 上传返回
     */
    @JsonProperty("result")
    private String result;

    /**
     * 审核状态
     */
    @JsonProperty("auditState")
    private String auditState;

    /**
     * 更新时间
     */
    @JsonProperty("updatedAt")
    private String updatedAt;
}
