package com.hlqz.lpg.lanyang.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Karbob
 * @date 2024-01-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LyDeliveryResponse {

    /**
     * 配送状态
     */
    @JsonProperty("state")
    private String state;

    /**
     * 配送数据
     */
    @JsonProperty("data")
    private List<DeliveryData> data;

    /**
     * 错误信息
     */
    @JsonProperty("errorMsg")
    private String message;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeliveryData {

        /**
         * 规格
         */
        @JsonProperty("Spec")
        private String spec;

        /**
         * 序列号
         */
        @JsonProperty("BID")
        private String serialNo;

        /**
         * 客户手机号
         */
        @JsonProperty("CustTel")
        private String crMobile;

        /**
         * 客户住址
         */
        @JsonProperty("CustAddress")
        private String crAddress;

        @JsonProperty("Code")
        private String code;

        @JsonProperty("NotExists")
        private String notExists;

        @JsonProperty("NotPass")
        private String notPass;
    }
}
