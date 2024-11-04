package com.hlqz.lpg.mybatis.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.hlqz.lpg.model.entity.Cylinder;
import com.hlqz.lpg.mybatis.dao.CylinderRepository;
import com.hlqz.lpg.mybatis.mapper.CylinderMapper;
import org.springframework.stereotype.Service;

/**
 * @author Karbob
 * @date 2024-01-09
 */
@Service
public class CylinderRepositoryImpl extends MPJBaseServiceImpl<CylinderMapper, Cylinder> implements CylinderRepository {

    @Override
    public IPage<Cylinder> fetchBarcodePageUsingLike(String barcode, IPage<Cylinder> page) {
        return lambdaQuery().select(Cylinder::getBarcode)
            .like(Cylinder::getBarcode, barcode)
            .page(page);
    }

    @Override
    public Cylinder fetchBySerialNo(String serialNo) {
        return lambdaQuery().eq(Cylinder::getSerialNo, serialNo)
            .one();
    }

    @Override
    public Cylinder fetchByBarcode(String barcode) {
        return lambdaQuery().eq(Cylinder::getBarcode, barcode)
            .one();
    }

    @Override
    public boolean existsByBarcode(String barcode) {
        return lambdaQuery().eq(Cylinder::getBarcode, barcode)
            .exists();
    }
}
