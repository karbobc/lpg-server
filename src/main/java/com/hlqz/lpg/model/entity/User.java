package com.hlqz.lpg.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.hlqz.lpg.model.enums.UserStateEnum;
import com.hlqz.lpg.mybatis.handler.CryptoTypeHandler;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Karbob
 * @date 2023-11-21
 */
@Data
@TableName(value = "t_user", autoResultMap = true)
public class User {

    /**
     * 主键 ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户真实姓名
     */
    @TableField(value = "real_name")
    private String realName;

    /**
     * 手机号码
     */
    @TableField(value = "mobile", typeHandler = CryptoTypeHandler.class)
    private String mobile;

    /**
     * 住址
     */
    @TableField(value = "address", typeHandler = CryptoTypeHandler.class)
    private String address;

    /**
     * 兰洋系统客户编号
     */
    @TableField(value = "ly_cr_no")
    private Integer lyCrNo;

    /**
     * 用户状态
     */
    @TableField(value = "state")
    private UserStateEnum state;

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
