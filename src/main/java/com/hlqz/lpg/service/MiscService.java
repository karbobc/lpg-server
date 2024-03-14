package com.hlqz.lpg.service;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.StrUtil;
import com.hlqz.lpg.constant.RegexConstants;
import com.hlqz.lpg.model.dto.EnrollDTO;
import com.hlqz.lpg.model.entity.Delivery;
import com.hlqz.lpg.model.entity.User;
import com.hlqz.lpg.model.enums.DeliveryStateEnum;
import com.hlqz.lpg.mybatis.dao.CylinderDAO;
import com.hlqz.lpg.mybatis.dao.DeliveryDAO;
import com.hlqz.lpg.mybatis.dao.UserDAO;
import com.hlqz.lpg.util.AssertionUtils;
import com.hlqz.lpg.util.RegexUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Karbob
 * @date 2024-01-09
 */
@Slf4j
@Service
public class MiscService {

    @Resource
    private UserDAO userDAO;
    @Resource
    private CylinderDAO cylinderDAO;
    @Resource
    private DeliveryDAO deliveryDAO;

    public void enroll(EnrollDTO dto) {
        // 参数校验
        final var realName = StrUtil.cleanBlank(dto.getRealName());
        final var mobile = StrUtil.cleanBlank(dto.getMobile());
        final var address = StrUtil.cleanBlank(dto.getAddress());
        final var barcode = dto.getBarcode();
        AssertionUtils.assertTrue(Validator.isChineseName(realName), "请输入正确的姓名");
        AssertionUtils.assertTrue(PhoneUtil.isMobile(mobile) || PhoneUtil.isTel(mobile), "请输入正确的手机号码");
        AssertionUtils.assertTrue(RegexUtils.matches(RegexConstants.USER_ADDRESS, address), "请输入正确的住址");
        // 查询用户和气瓶
        final var cylinder = cylinderDAO.fetchByBarcode(barcode);
        AssertionUtils.assertNotNull(cylinder, "气瓶条码不存在");
        var user = userDAO.fetchByRealNameAndMobile(realName, mobile);
        if (Objects.isNull(user)) {
            user = new User();
            user.setRealName(realName);
            user.setMobile(mobile);
            user.setAddress(address);
            userDAO.save(user);
        }
        // 重复提交保证接口幂等
        // 配送信息表中, 一个用户可以对应多个不同的气瓶条码, 但是状态不能是未配送或配送失败的
        // 一个气瓶条码也可以对应多个用户, 对应多个用户的时候, 最多只能有一个未配送或配送失败的, 剩下的全部是配送成功的
        if (deliveryDAO.existsByUserIdAndBarcodeAndStates(user.getId(), barcode, DeliveryStateEnum.NOT_STARTED, DeliveryStateEnum.CRASH)) {
            log.warn("MiscService, 重复配送, userId: {}, name: {}, mobile: {}, address: {}", user.getId(), realName, mobile, address);
            return;
        }
        // 保存配送信息
        final var delivery = new Delivery();
        delivery.setUserId(user.getId());
        delivery.setCylinderId(cylinder.getId());
        deliveryDAO.save(delivery);
    }
}
