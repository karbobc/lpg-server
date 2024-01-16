package com.hlqz.lpg.controller;

import com.hlqz.lpg.model.common.ApiResult;
import com.hlqz.lpg.model.dto.EnrollDTO;
import com.hlqz.lpg.model.param.EnrollParam;
import com.hlqz.lpg.service.MiscService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
