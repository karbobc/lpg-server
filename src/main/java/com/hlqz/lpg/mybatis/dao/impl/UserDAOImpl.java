package com.hlqz.lpg.mybatis.dao.impl;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.hlqz.lpg.model.entity.User;
import com.hlqz.lpg.mybatis.dao.UserDAO;
import com.hlqz.lpg.mybatis.mapper.UserMapper;
import com.hlqz.lpg.util.AesUtils;
import com.hlqz.lpg.util.ConfigUtils;
import org.springframework.stereotype.Service;

/**
 * @author Karbob
 * @date 2023-11-21
 */
@Service
public class UserDAOImpl extends MPJBaseServiceImpl<UserMapper, User> implements UserDAO {

    @Override
    public User fetchByRealNameAndMobile(String realName, String mobile) {
        return lambdaQuery().eq(User::getRealName, realName)
            .eq(User::getMobile, AesUtils.encrypt(mobile, ConfigUtils.getDatabaseAesKey()))
            .one();
    }
}
