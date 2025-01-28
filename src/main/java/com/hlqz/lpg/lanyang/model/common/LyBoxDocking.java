package com.hlqz.lpg.lanyang.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hlqz.lpg.lanyang.model.enums.LyBoxDockingAuditStateEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Karbob
 * @date 2025-01-28
 */
@Data
@NoArgsConstructor
public class LyBoxDocking {

    /**
     * ID
     */
    @JsonProperty("Id")
    private String id;

    /**
     * 钢印号
     */
    @JsonProperty("EquNo")
    private String serialNo;

    /**
     * 气瓶条码
     */
    @JsonProperty("QRcode")
    private String barcode;

    /**
     * 上传返回
     */
    @JsonProperty("RspState")
    private String rspState;

    /**
     * 审核状态
     */
    @JsonProperty("AuditState")
    private LyBoxDockingAuditStateEnum auditState;

    /**
     * 最近提交时间
     */
    @JsonProperty("UpdateTime")
    private String updateTime;

    /**
     * 首次提交时间
     */
    @JsonProperty("CreateTime")
    private String CreateTime;
}
