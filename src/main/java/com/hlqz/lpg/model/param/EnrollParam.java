package com.hlqz.lpg.model.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Karbob
 * @date 2024-01-09
 */
@Data
@NoArgsConstructor
public class EnrollParam {

    /**
     * 用户真实姓名
     */
    @NotBlank
    @JsonProperty("name")
    private String name;

    /**
     * 用户手机号
     */
    @NotBlank
    @JsonProperty("mobile")
    private String mobile;

    /**
     * 用户住址
     */
    @NotBlank
    @JsonProperty("address")
    private String address;

    /**
     * 气瓶条码
     */
    @NotBlank
    @JsonProperty("barcode")
    private String barcode;
}
