package com.hlqz.lpg.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.hlqz.lpg.model.enums.DeliveryStateEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Karbob
 * @date 2024-01-06
 */
@Data
@TableName(value = "t_delivery", autoResultMap = true)
public class Delivery {

    /**
     * 主键 ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户主键 ID, 关联 t_user
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 钢瓶主键 ID, 关联 t_cylinder
     */
    @TableField(value = "cylinder_id")
    private Long cylinderId;

    /**
     * 配送状态
     */
    @TableField(value = "state")
    private DeliveryStateEnum state;

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
