package com.hlqz.lpg.lanyang.model.common;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Karbob
 * @date 2024-01-06
 * <p>
 * 兰洋系统客户信息
 */
@Data
@NoArgsConstructor
public class LyCustomer {

    /**
     * 客户 ID
     */
    @JsonProperty("cusId")
    @JsonAlias({"CustId"})
    private String crId;

    /**
     * 客户编号
     */
    @JsonProperty("cusNo")
    @JsonAlias({"CustNo"})
    private Integer crNo;

    /**
     * 客户姓名
     */
    @JsonProperty("cusName")
    @JsonAlias({"CustName"})
    private String crName;

    /**
     * 客户类型, 1-居民用户
     */
    @JsonProperty("custType")
    @JsonAlias({"CustType"})
    private Integer crType;

    /**
     * 客户电话
     */
    @JsonProperty("tel")
    @JsonAlias({"Tel"})
    private String mobile;

    /**
     * 详细地址
     */
    @JsonProperty("address")
    @JsonAlias({"Address"})
    private String address;

    /**
     * 证件号码
     */
    @JsonProperty("idcs")
    @JsonAlias({"IDCards"})
    private String idNo;

    /**
     * 证件类型, 0-身份证
     */
    @JsonProperty("CardType")
    @JsonAlias({"CardType"})
    private Integer idType;

    /**
     * 证件地址
     */
    @JsonProperty("addressIDCard")
    @JsonAlias({"AddressIDCard"})
    private String idAddress;

    /**
     * 流转周期
     */
    @JsonProperty("Cycle")
    @JsonAlias({"Cycle"})
    private Integer cycle;

    /**
     * 经度
     */
    @JsonProperty("pX")
    @JsonAlias("PointX")
    private BigDecimal longitude;

    /**
     * 维度
     */
    @JsonProperty("pY")
    @JsonAlias("PointY")
    private BigDecimal latitude;

    /**
     * 备注
     */
    @JsonProperty("remark")
    @JsonAlias({"Remark"})
    private String remark;

    /**
     * 所属单位 ID
     */
    @JsonProperty("sectionId")
    @JsonAlias({"SectionId"})
    private Long sectionId;

    /**
     * 所属单位名称
     */
    @JsonAlias({"SectionName"})
    private String sectionName;

}
