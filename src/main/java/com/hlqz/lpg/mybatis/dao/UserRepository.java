package com.hlqz.lpg.mybatis.dao;

import com.github.yulichang.base.MPJBaseService;
import com.hlqz.lpg.model.entity.User;

/**
 * @author Karbob
 * @date 2023-11-21
 */
public interface UserRepository extends MPJBaseService<User> {

    /**
     * 根据用户真实姓名和手机号查询用户
     * @param realName 真实姓名
     * @param mobile 手机号
     */
    User fetchByRealNameAndMobile(String realName, String mobile);
}
