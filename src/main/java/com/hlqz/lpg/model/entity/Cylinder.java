package com.hlqz.lpg.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.hlqz.lpg.model.enums.CylinderStateEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Karbob
 * @date 2024-01-05
 */
@Data
@TableName(value = "t_cylinder", autoResultMap = true)
public class Cylinder {

    /**
     * 主键 ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 钢印号
     */
    @TableField(value = "serial_no")
    private String serialNo;

    /**
     * 气瓶条码
     */
    @TableField(value = "barcode")
    private String barcode;

    /**
     * 钢瓶状态
     */
    @TableField(value = "state")
    private CylinderStateEnum state;

    /**
     * 版本号
     */
    @Version
    @TableField(value = "version")
    private Integer version;

    /**
     * 创建时间
     */
    @TableField(value = "created_at")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField(value = "updated_at")
    private LocalDateTime updatedAt;
}
