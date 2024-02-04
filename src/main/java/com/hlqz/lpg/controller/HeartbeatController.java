package com.hlqz.lpg.controller;

import com.hlqz.lpg.model.common.ApiResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Karbob
 * @date 2024-02-04
 */
@RestController
@RequestMapping("/heartbeat")
public class HeartbeatController extends BaseController {

    @GetMapping("/alive")
    public ApiResult<Void> alive() {
        return ApiResult.ok();
    }
}
