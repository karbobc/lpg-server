package com.hlqz.lpg.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Karbob
 * @date 2023-11-20
 */
public class BaseController {

    @Resource
    private HttpServletRequest request;
    @Resource
    private HttpServletResponse response;
}
