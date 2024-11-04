package com.hlqz.lpg.mybatis.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.base.MPJBaseService;
import com.hlqz.lpg.model.entity.Cylinder;

/**
 * @author Karbob
 * @date 2024-01-09
 */
public interface CylinderRepository extends MPJBaseService<Cylinder> {

    /**
     * 分页查询气瓶条码, 使用模糊查询
     * @param barcode 气瓶条码
     * @param page 分页参数
     */
    IPage<Cylinder> fetchBarcodePageUsingLike(String barcode, IPage<Cylinder> page);

    /**
     * 根据钢印号查询钢瓶信息
     * @param serialNo 钢印号
     */
    Cylinder fetchBySerialNo(String serialNo);

    /**
     * 根据气瓶条码查询钢瓶信息
     * @param barcode 气瓶条码
     */
    Cylinder fetchByBarcode(String barcode);

    /**
     * 根据气瓶条码判断钢瓶信息是否存在
     * @param barcode 气瓶条码
     */
    boolean existsByBarcode(String barcode);
}
