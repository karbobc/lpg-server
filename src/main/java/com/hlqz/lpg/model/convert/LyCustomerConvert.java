package com.hlqz.lpg.model.convert;

import com.hlqz.lpg.lanyang.model.common.LyCustomer;
import com.hlqz.lpg.model.vo.LyUploadResultCustomerVO;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author Karbob
 * @date 2024-10-01
 */
public class LyCustomerConvert {

    public static LyUploadResultCustomerVO toVO(LyCustomer lyCustomer) {
        final var vo = new LyUploadResultCustomerVO();
        vo.setName(lyCustomer.getCrName());
        vo.setMobile(lyCustomer.getMobile());
        vo.setAddress(lyCustomer.getAddress());
        return vo;
    }

    public static List<LyUploadResultCustomerVO> toVO(List<LyCustomer> lyCustomerList) {
        if (CollectionUtils.isEmpty(lyCustomerList)) {
            return Collections.emptyList();
        }
        return lyCustomerList.stream()
            .map(LyCustomerConvert::toVO)
            .toList();
    }
}
