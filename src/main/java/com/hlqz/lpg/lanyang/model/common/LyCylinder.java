package com.hlqz.lpg.lanyang.model.common;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.hlqz.lpg.lanyang.model.enums.LyCylinderPositionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Karbob
 * @date 2024-01-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LyCylinder {

    /**
     * 钢瓶 ID
     */
    @JsonAlias({"VaseId"})
    private String cylinderId;

    /**
     * 钢印号
     */
    @JsonAlias({"VaseCode"})
    private String cylinderNo;

    /**
     * 气瓶条码
     */
    @JsonAlias({"BarCode"})
    private String barcode;

    /**
     * 当前位置
     */
    @JsonAlias({"CurPosName"})
    private String currentPosition;

    /**
     * 位置类型
     */
    @JsonAlias({"CurPosTypeName"})
    private LyCylinderPositionTypeEnum currentPositionType;

    /**
     * 充装次数
     */
    @JsonAlias({"FillTimes"})
    private Integer fillTimes;

    /**
     * 上传状态
     */
    @JsonAlias({"UpLoadFlag"})
    private String uploadState;

    /**
     * 上传结果
     */
    @JsonAlias({"UpLoadReturn"})
    private String uploadResult;

    /**
     * 所属单位 ID
     */
    @JsonAlias({"SectionId"})
    private Long sectionId;

    /**
     * 所属单位名称
     */
    @JsonAlias({"SectionName"})
    private String sectionName;
}
