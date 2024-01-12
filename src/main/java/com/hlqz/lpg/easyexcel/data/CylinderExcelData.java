package com.hlqz.lpg.easyexcel.data;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Karbob
 * @date 2024-01-11
 */
@Data
@NoArgsConstructor
public class CylinderExcelData {

    /**
     * 钢瓶编号
     */
    @ExcelProperty("产品编号")
    private String serialNo;

    /**
     * 气瓶条码
     */
    @ExcelProperty("单位内编号")
    private String barcode;
}
