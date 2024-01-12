package com.hlqz.lpg.service.helper;

import com.hlqz.lpg.model.entity.Cylinder;
import com.hlqz.lpg.model.vo.CylinderSearchVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author Karbob
 * @date 2024-01-09
 */
@Service
public class CylinderServiceHelper {

    public List<CylinderSearchVO> convertToCylinderSearchVO(List<Cylinder> cylinderList) {
        if (CollectionUtils.isEmpty(cylinderList)) {
            return Collections.emptyList();
        }
        return cylinderList.stream()
            .map(this::convertToCylinderSearchVO)
            .toList();
    }

    public CylinderSearchVO convertToCylinderSearchVO(Cylinder cylinder) {
        final CylinderSearchVO vo = new CylinderSearchVO();
        vo.setBarcode(cylinder.getBarcode());
        return vo;
    }
}
