package com.hlqz.lpg.model.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
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
public class CylinderSearchParam {

    @NotBlank
    @JsonProperty("barcode")
    private String barcode;
}
