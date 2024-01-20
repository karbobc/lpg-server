package com.hlqz.lpg.lanyang.service;

import cn.hutool.core.codec.Base64;
import com.hlqz.lpg.lanyang.model.common.LyCylinder;
import com.hlqz.lpg.lanyang.model.enums.LyOrderTypeEnum;
import com.hlqz.lpg.lanyang.model.request.LyFetchByPageParam;
import com.hlqz.lpg.lanyang.model.response.LyPageResponse;
import com.hlqz.lpg.util.AssertionUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author Karbob
 * @date 2024-01-18
 */
@Slf4j
@Service
public class LyService {

    @Resource
    private LyApiService lyApiService;

    @Resource
    private LyMApiService lyMApiService;

    /**
     * 根据气瓶条码查询钢瓶数据
     * @param barcodes 气瓶条码
     * @return 钢瓶数据
     */
    public List<LyCylinder> fetchByBarcodes(String... barcodes) {
        final var param = new LyFetchByPageParam();
        final var sqlWhere = StringUtils.join("BarCode IN ('", StringUtils.join(barcodes, "', '"), "')");
        param.setPage(1);
        param.setSize(barcodes.length);
        param.setSqlWhere(Base64.encode(sqlWhere));
        param.setOrderBy("UpdTime");
        param.setOrderType(LyOrderTypeEnum.DESC);
        LyPageResponse<LyCylinder> response = lyApiService.fetchCylinder(param);
        AssertionUtils.assertNotNull(response, "接口请求异常");
        if (CollectionUtils.isEmpty(response.getData())) {
            return Collections.emptyList();
        }
        return response.getData();
    }

    public List<LyCylinder> fetchBySerialNos(String... serialNos) {
        final var param = new LyFetchByPageParam();
        final var sqlWhere = StringUtils.join("VaseVo IN ('", StringUtils.join(serialNos, "', '"), "')");
        param.setPage(1);
        param.setSize(serialNos.length);
        param.setSqlWhere(Base64.encode(sqlWhere));
        param.setOrderBy("UpdTime");
        param.setOrderType(LyOrderTypeEnum.DESC);
        LyPageResponse<LyCylinder> response = lyApiService.fetchCylinder(param);
        AssertionUtils.assertNotNull(response, "接口请求异常");
        if (CollectionUtils.isEmpty(response.getData())) {
            return Collections.emptyList();
        }
        return response.getData();
    }
}
