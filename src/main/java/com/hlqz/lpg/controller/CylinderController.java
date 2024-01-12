package com.hlqz.lpg.controller;

import com.hlqz.lpg.model.common.ApiResult;
import com.hlqz.lpg.model.dto.CylinderSearchDTO;
import com.hlqz.lpg.model.dto.CylinderUploadDTO;
import com.hlqz.lpg.model.param.CylinderSearchParam;
import com.hlqz.lpg.model.param.CylinderUploadParam;
import com.hlqz.lpg.model.vo.CylinderSearchVO;
import com.hlqz.lpg.model.vo.CylinderUploadVO;
import com.hlqz.lpg.service.CylinderService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Karbob
 * @date 2024-01-09
 */
@RestController
@RequestMapping("/cylinder")
public class CylinderController extends BaseController{

    @Resource
    private CylinderService cylinderService;

    @GetMapping("/s")
    public ApiResult<List<CylinderSearchVO>> search(@Valid CylinderSearchParam param) {
        final var dto = new CylinderSearchDTO();
        dto.setBarcode(param.getBarcode());
        return ApiResult.ok(cylinderService.search(dto));
    }

    @PostMapping("/upload")
    public ApiResult<List<CylinderUploadVO>> upload(@Valid CylinderUploadParam param) {
        final var dto = new CylinderUploadDTO();
        dto.setFiles(param.getFiles());
        return ApiResult.ok(cylinderService.importFromExcel(dto));
    }
}
