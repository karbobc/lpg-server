package com.hlqz.lpg.controller;

import com.hlqz.lpg.model.common.ApiResult;
import com.hlqz.lpg.model.dto.EnrollDTO;
import com.hlqz.lpg.model.dto.LyBoxDockingDTO;
import com.hlqz.lpg.model.dto.LyUploadResultDTO;
import com.hlqz.lpg.model.param.EnrollParam;
import com.hlqz.lpg.model.vo.LyBoxDockingVO;
import com.hlqz.lpg.model.vo.LyUploadResultVO;
import com.hlqz.lpg.service.MiscService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * @author Karbob
 * @date 2024-01-06
 */
@RestController
@RequestMapping("/misc")
public class MiscController extends BaseController {

    @Resource
    private MiscService miscService;

    @PostMapping("/enroll")
    public ApiResult<Void> enroll(@Valid @RequestBody EnrollParam param) {
        final var dto = new EnrollDTO();
        dto.setRealName(param.getName());
        dto.setMobile(param.getMobile());
        dto.setAddress(param.getAddress());
        dto.setBarcode(param.getBarcode());
        miscService.enroll(dto);
        return ApiResult.ok();
    }

    @GetMapping("/ly/upload/result")
    public ApiResult<LyUploadResultVO> fetchUploadResult(@RequestParam String barcode,
                                                         @RequestParam(defaultValue = "false") Boolean trace) {
        final var dto = new LyUploadResultDTO();
        dto.setBarcode(barcode);
        dto.setTrace(trace);
        return ApiResult.ok(miscService.fetchUploadResult(dto));
    }

    @GetMapping("/ly/v2/upload/result")
    public ApiResult<LyBoxDockingVO> fetchBoxDocking(@RequestParam String barcode,
                                                     @RequestParam(defaultValue = "false") Boolean trace) {
        final var dto = new LyBoxDockingDTO();
        dto.setBarcode(barcode);
        dto.setTrace(trace);
        return ApiResult.ok(miscService.fetchBoxDocking(dto));
    }
}
