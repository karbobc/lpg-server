package com.hlqz.lpg.model.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Karbob
 * @date 2024-01-12
 */
@Data
@NoArgsConstructor
public class CylinderUploadParam {

    /**
     * 上传文件列表
     */
    @NotEmpty
    @JsonProperty("files")
    private List<MultipartFile> files;

    /**
     * 自动纠正重复数据
     */
    @JsonProperty("repair")
    private Boolean repair;
}
