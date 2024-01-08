package com.hlqz.lpg.lanyang.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.hlqz.lpg.util.DateTimeUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Karbob
 * @date 2024-01-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LyDeliveryParam {

    /**
     * 客户编号
     */
    @JsonProperty("custNo")
    private String crNo;

    /**
     * 客户姓名
     */
    @JsonProperty("custName")
    private String crName;

    /**
     * 配送日期
     */
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = DateTimeUtils.PATTERN_DATE)
    private LocalDate deliveryDate;

    /**
     * 气瓶条码, 多个条码英文逗号分隔
     */
    @JsonProperty("heavyCodes")
    private String barcode;

    /**
     * APP 定位地址
     */
    @JsonProperty("BackAddress")
    private String address;

    /**
     * 经度
     */
    @JsonProperty("PointX")
    private BigDecimal longitude;

    /**
     * 维度
     */
    @JsonProperty("PointY")
    private BigDecimal latitude;

    /**
     * 备注
     */
    @JsonProperty("Remark")
    private String remark;

    /**
     * 序列号
     */
    @JsonProperty("BID")
    private String serialNo;

    /**
     * 操作员 ID
     */
    @JsonProperty("optId")
    private Long opId;

    /**
     * 操作员姓名
     */
    @JsonProperty("optName")
    private String opName;

    /**
     * 所属单位 ID
     */
    @JsonProperty("sectionId")
    private Long sectionId;

    /**
     * 所属单位名称
     */
    @JsonProperty("sectionName")
    private String sectionName;

    /**
     * 订单 ID
     */
    @JsonProperty("OrderID")
    private Long orderId;

    /**
     * 账单编号
     */
    @JsonProperty("BillNO")
    private String billNo;

    /**
     * 司机 ID
     */
    @JsonProperty("DriverId")
    private Long driverId;

    /**
     * 司机姓名
     */
    @JsonProperty("DriverName")
    private String driverName;

    @JsonProperty("SynTab")
    private Integer syncTab = 1;
}
