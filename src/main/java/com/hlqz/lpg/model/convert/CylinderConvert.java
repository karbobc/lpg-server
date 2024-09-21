package com.hlqz.lpg.model.convert;

import com.hlqz.lpg.lanyang.model.common.LyCylinder;
import com.hlqz.lpg.model.entity.Cylinder;
import com.hlqz.lpg.model.vo.CylinderSearchVO;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author Karbob
 * @date 2024-09-22
 */
public class CylinderConvert {

    public static Cylinder from(LyCylinder lyCylinder) {
        final var cylinder = new Cylinder();
        cylinder.setBarcode(lyCylinder.getBarcode());
        cylinder.setSerialNo(lyCylinder.getSerialNo());
        return cylinder;
    }

    public static CylinderSearchVO toVO(Cylinder cylinder) {
        final CylinderSearchVO vo = new CylinderSearchVO();
        vo.setBarcode(cylinder.getBarcode());
        return vo;
    }

    public static List<CylinderSearchVO> toVO(List<Cylinder> cylinderList) {
        if (CollectionUtils.isEmpty(cylinderList)) {
            return Collections.emptyList();
        }
        return cylinderList.stream()
            .map(CylinderConvert::toVO)
            .toList();
    }
}
