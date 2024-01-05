package com.hlqz.lpg.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hlqz.lpg.mybatis.dao.UserDAO;
import com.hlqz.lpg.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Karbob
 * @date 2023-11-21
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;
}
