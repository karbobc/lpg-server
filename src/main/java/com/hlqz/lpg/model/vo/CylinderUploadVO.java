package com.hlqz.lpg.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Karbob
 * @date 2024-01-12
 */
@Data
@NoArgsConstructor
public class CylinderUploadVO {

    /**
     * 上传的文件名
     */
    @JsonProperty("fileName")
    private String fileName;

    /**
     * 上传的文件是否处理成功
     */
    @JsonProperty("success")
    private Boolean success;

    /**
     * 处理失败提示
     */
    @JsonProperty("message")
    private String message;

    /**
     * Excel 与 Database 中不一致数据
     */
    @JsonProperty("diff")
    private List<CylinderDiffVO> diffList;
}
