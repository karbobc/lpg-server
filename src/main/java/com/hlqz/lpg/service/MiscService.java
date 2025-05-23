package com.hlqz.lpg.service;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.StrUtil;
import com.hlqz.lpg.constant.RegexConstants;
import com.hlqz.lpg.lanyang.model.enums.LyCylinderUploadStateEnum;
import com.hlqz.lpg.lanyang.service.LyService;
import com.hlqz.lpg.model.convert.CylinderConvert;
import com.hlqz.lpg.model.convert.DeliveryConvert;
import com.hlqz.lpg.model.convert.LyCustomerConvert;
import com.hlqz.lpg.model.convert.UserConvert;
import com.hlqz.lpg.model.dto.EnrollDTO;
import com.hlqz.lpg.model.dto.LyBoxDockingDTO;
import com.hlqz.lpg.model.dto.LyUploadResultDTO;
import com.hlqz.lpg.model.enums.DeliveryStateEnum;
import com.hlqz.lpg.model.vo.LyBoxDockingVO;
import com.hlqz.lpg.model.vo.LyUploadResultVO;
import com.hlqz.lpg.mybatis.dao.CylinderRepository;
import com.hlqz.lpg.mybatis.dao.DeliveryRepository;
import com.hlqz.lpg.mybatis.dao.UserRepository;
import com.hlqz.lpg.util.AssertionUtils;
import com.hlqz.lpg.util.JsonUtils;
import com.hlqz.lpg.util.RegexUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

/**
 * @author Karbob
 * @date 2024-01-09
 */
@Slf4j
@Service
public class MiscService {

    @Resource
    private UserRepository userRepository;
    @Resource
    private CylinderRepository cylinderRepository;
    @Resource
    private DeliveryRepository deliveryRepository;
    @Resource
    private LyService lyService;

    public void enroll(EnrollDTO dto) {
        // 参数校验
        final var realName = StrUtil.cleanBlank(dto.getRealName());
        final var mobile = StrUtil.cleanBlank(dto.getMobile());
        final var address = StrUtil.cleanBlank(dto.getAddress());
        final var barcode = dto.getBarcode();
        AssertionUtils.assertTrue(Validator.isChineseName(realName), "请输入正确的姓名");
        AssertionUtils.assertTrue(PhoneUtil.isMobile(mobile) || PhoneUtil.isTel(mobile), "请输入正确的手机号码");
        AssertionUtils.assertTrue(RegexUtils.matches(RegexConstants.USER_ADDRESS, address), "请输入正确的住址");
        // 查询钢瓶档案, 本地数据库不存在, 则查询兰洋系统
        var cylinder = cylinderRepository.fetchByBarcode(barcode);
        if (Objects.isNull(cylinder)) {
            final var lyCylinder = lyService.fetchCylinderByBarcodes(barcode);
            AssertionUtils.assertNotEmpty(lyCylinder, "气瓶条码不存在");
            cylinder = CylinderConvert.from(lyCylinder.getFirst());
            cylinderRepository.save(cylinder);
        }
        // 查询用户数据
        var user = userRepository.fetchByRealNameAndMobile(realName, mobile);
        if (Objects.isNull(user)) {
            user = UserConvert.from(dto);
            userRepository.save(user);
        }
        // 重复提交保证接口幂等
        // 配送信息表中, 一个用户可以对应多个不同的气瓶条码, 但是状态不能是未配送或配送失败的
        // 一个气瓶条码也可以对应多个用户, 对应多个用户的时候, 最多只能有一个未配送或配送失败的, 剩下的全部是配送成功的
        if (deliveryRepository.existsByUserIdAndBarcodeAndStates(user.getId(), barcode, DeliveryStateEnum.NOT_STARTED, DeliveryStateEnum.CRASH)) {
            log.warn("MiscService, 重复配送, userId: {}, name: {}, mobile: {}, address: {}", user.getId(), realName, mobile, address);
            return;
        }
        // 保存配送信息
        final var delivery = DeliveryConvert.from(user, cylinder);
        deliveryRepository.save(delivery);
    }

    public LyUploadResultVO fetchUploadResult(LyUploadResultDTO dto) {
        final var lyCylinderList = lyService.fetchCylinderByBarcodes(dto.getBarcode());
        AssertionUtils.assertNotEmpty(lyCylinderList, "未查询到钢瓶信息");
        final var lyCylinder = lyCylinderList.getFirst();
        final var vo = new LyUploadResultVO();
        vo.setBarcode(lyCylinder.getBarcode());
        vo.setSerialNo(lyCylinder.getSerialNo());
        if (Boolean.TRUE.equals(dto.getTrace())) {
            final var nameSet = lyService.fetchTraceCustomerNameSet(lyCylinder.getCylinderId());
            if (CollectionUtils.isNotEmpty(nameSet)) {
                final var lyCustomerList = lyService.fetchCustomerByNames(nameSet.toArray(new String[0]));
                vo.setCustomers(LyCustomerConvert.toVO(lyCustomerList));
            } else {
                vo.setCustomers(Collections.emptyList());
            }
        }
        if (lyCylinder.getUploadState() == LyCylinderUploadStateEnum.UPLOADED) {
            vo.setResult("已上传");
            return vo;
        }
        if (StringUtils.isBlank(lyCylinder.getUploadResult())) {
            vo.setResult("上传返回空白，可能图片缺失");
            return vo;
        }
        try {
            final var uploadResult = JsonUtils.toMap(lyCylinder.getUploadResult(), String.class);
            vo.setResult(uploadResult.get("Value"));
        } catch (Exception e) {
            vo.setResult(lyCylinder.getUploadResult());
        }
        return vo;
    }

    public LyBoxDockingVO fetchBoxDocking(LyBoxDockingDTO dto) {
        final var lyBoxDockingList = lyService.fetchBoxDocking(dto.getBarcode());
        AssertionUtils.assertNotEmpty(lyBoxDockingList, "未查询到钢瓶信息");
        final var boxDocking = lyBoxDockingList.getFirst();
        final var auditState = boxDocking.getAuditState();
        final var vo = new LyBoxDockingVO();
        vo.setBarcode(boxDocking.getBarcode());
        vo.setSerialNo(boxDocking.getSerialNo());
        vo.setResult(boxDocking.getRspState());
        vo.setAuditState(Objects.isNull(auditState) ? StringUtils.EMPTY : auditState.getDesc());
        vo.setUpdatedAt(boxDocking.getUpdateTime());
        return vo;
    }
}
