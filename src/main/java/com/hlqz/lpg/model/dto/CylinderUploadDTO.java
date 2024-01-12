package com.hlqz.lpg.model.dto;

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
public class CylinderUploadDTO {

    /**
     * 上传文件列表
     */
    public List<MultipartFile> files;
}
