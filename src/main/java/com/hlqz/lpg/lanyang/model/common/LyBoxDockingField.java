package com.hlqz.lpg.lanyang.model.common;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LyBoxDockingField {

    /**
     * 钢印号
     */
    @JsonProperty("equno")
    private String serialNo;

    /**
     * 气瓶条码
     */
    @JsonProperty("qRcode")
    private String barcode;

    /**
     * 平台编号
     */
    @JsonProperty("ExcuteMethod")
    private String platformNo;

    /**
     * 审核状态
     */
    @JsonProperty("AuditState")
    private LyBoxDockingAuditStateEnum auditState;

    /**
     * 最近提交时间
     */
    @JsonProperty("UpTimeStr")
    private String updateTime;

    /**
     * 首次提交时间
     */
    @JsonProperty("AddTimeStr")
    private String createTime;
}
