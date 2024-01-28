package com.hlqz.lpg.lanyang.service;

import com.hlqz.lpg.lanyang.helper.LyHelper;
import com.hlqz.lpg.lanyang.model.common.LyCustomer;
import com.hlqz.lpg.lanyang.model.common.LyCylinder;
import com.hlqz.lpg.lanyang.model.dto.LyDeliveryDTO;
import com.hlqz.lpg.lanyang.model.dto.LySaveCustomerDTO;
import com.hlqz.lpg.lanyang.model.enums.LyOrderTypeEnum;
import com.hlqz.lpg.lanyang.model.request.LyFetchByPageParam;
import com.hlqz.lpg.lanyang.model.response.LyDeliveryResponse;
import com.hlqz.lpg.lanyang.model.response.LyPageResponse;
import com.hlqz.lpg.util.AssertionUtils;
import com.hlqz.lpg.util.JsonUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
     * 根据 SQL 条件查询钢瓶数据, 并限制数据返回的个数
     * @param sqlWhere SQL 条件
     * @param limit 限制个数
     * @return 钢瓶数据
     */
    public List<LyCylinder> fetchCylinderBySqlWhereWithLimit(String sqlWhere, Integer limit) {
        final var param = LyHelper.buildCylinderPageParam();
        if (Objects.nonNull(limit)) {
            param.setSize(limit);
        }
        param.setSqlWhere(LyHelper.buildEncryptedSqlWhere(sqlWhere));
        LyPageResponse<LyCylinder> response = lyApiService.fetchCylinder(param);
        AssertionUtils.assertNotNull(response, "接口请求异常");
        if (CollectionUtils.isEmpty(response.getData())) {
            return Collections.emptyList();
        }
        return response.getData();
    }

    /**
     * 根据 SQL 条件查询钢瓶数据
     * @param sqlWhere SQL 条件
     * @return 钢瓶数据
     */
    public List<LyCylinder> fetchCylinderBySqlWhere(String sqlWhere) {
        return fetchCylinderBySqlWhereWithLimit(sqlWhere, null);
    }

    /**
     * 根据气瓶条码查询钢瓶数据
     * @param barcodes 气瓶条码
     * @return 钢瓶数据
     */
    public List<LyCylinder> fetchCylinderByBarcodes(String... barcodes) {
        final var sqlWhere = LyHelper.buildSqlWhereForIn("BarCode", Arrays.asList(barcodes));
        return fetchCylinderBySqlWhereWithLimit(sqlWhere, barcodes.length);
    }

    /**
     * 根据钢印号查询钢瓶数据
     * @param serialNos 钢印号
     * @return 钢瓶数据
     */
    public List<LyCylinder> fetchCylinderBySerialNos(String... serialNos) {
        final var sqlWhere = LyHelper.buildSqlWhereForIn("VaseVo", Arrays.asList(serialNos));
        return fetchCylinderBySqlWhereWithLimit(sqlWhere, serialNos.length);
    }

    /**
     * 根据自定义参数查询客户数据
     * @param param 自定义参数
     * @return 客户数据
     */
    public List<LyCustomer> fetchCustomerByParam(LyFetchByPageParam param) {
        if (StringUtils.isNoneBlank(param.getSqlWhere())) {
            param.setSqlWhere(LyHelper.buildEncryptedSqlWhere(param.getSqlWhere()));
        }
        LyPageResponse<LyCustomer> response = lyApiService.fetchCustomer(param);
        AssertionUtils.assertNotNull(response, "接口请求异常");
        if (CollectionUtils.isEmpty(response.getData())) {
            return Collections.emptyList();
        }
        return response.getData();
    }

    /**
     * 获取一个可用的客户号, 默认取 crNo >= 2w and <= 10w
     * @return 可使用客户号
     */
    public Integer fetchAvailableCrNo() {
        final var min = 2_0000;
        final var max = 10_0000;
        final var param = LyHelper.buildCustomerPageParam();
        final var sqlWhere = StringUtils.join(
            LyHelper.buildSqlWhereForGe("CustNo", min),
            LyHelper.appendSqlWhereForLe("CustNo", max, "AND")
        );
        param.setSqlWhere(sqlWhere);
        param.setSize(1);
        param.setOrderBy("CustNo");
        param.setOrderType(LyOrderTypeEnum.DESC);
        final var result = fetchCustomerByParam(param);
        if (CollectionUtils.isEmpty(result)) {
            return min;
        }
        return result.getFirst().getCrNo() + 1;
    }

    /**
     * 根据客户姓名和手机号码查询客户数据
     * @param name 姓名
     * @param mobile 手机号码
     * @return 客户数据
     */
    public List<LyCustomer> fetchCustomerByNameAndMobile(String name, String mobile) {
        final var param = LyHelper.buildCustomerPageParam();
        final var sqlWhere = StringUtils.join(
            LyHelper.buildSqlWhereForEq("CustName", name),
            LyHelper.appendSqlWhereForEq("Tel", mobile, "AND")
        );
        param.setSqlWhere(sqlWhere);
        return fetchCustomerByParam(param);
    }

    /**
     * 根据兰洋系统的客户号, 查询对应的客户数据
     * @param crNo 客户号
     * @return 客户数据
     */
    public LyCustomer fetchCustomerByCrNo(Integer crNo) {
        final var param = LyHelper.buildCustomerPageParam();
        final var sqlWhere = LyHelper.buildSqlWhereForEq("CustNo", crNo);
        param.setSqlWhere(sqlWhere);
        final var result = fetchCustomerByParam(param);
        if (CollectionUtils.isEmpty(result)) {
            return null;
        }
        return result.getFirst();
    }

    /**
     * 新增客户信息
     * @param dto 新增客户信息 DTO
     */
    public LyCustomer saveCustomer(LySaveCustomerDTO dto) {
        final var param = LyHelper.buildSaveCustomerParam(dto);
        final var response = lyApiService.saveCustomer(param);
        AssertionUtils.assertNotNull(response, "接口请求异常");
        AssertionUtils.assertTrue(response.getSuccess(), StringUtils.defaultIfBlank(response.getMessage(), "新增客户信息失败"));
        return param;
    }

    /**
     * 钢瓶配送, 同步到兰洋系统
     * @param dto 钢瓶配送 DTO
     */
    public void delivery(LyDeliveryDTO dto) {
        final var param = LyHelper.buildDeliveryParam(dto);
        final var paramMap = JsonUtils.toMap(JsonUtils.toJson(param));
        final var response = lyMApiService.delivery(paramMap);
        final var responseJsonBody = response.substring(0, response.indexOf("<"));
        log.info("LyService, 兰洋系统配送接口请求成功, body: {}", responseJsonBody);
        final LyDeliveryResponse deliveryResponse = JsonUtils.fromJson(responseJsonBody, LyDeliveryResponse.class);
        AssertionUtils.assertNotNull(deliveryResponse, "兰洋系统配送请求解析 JSON 失败");
        AssertionUtils.assertEquals(deliveryResponse.getState(), "1",
            StringUtils.defaultIfBlank(deliveryResponse.getMessage(), "兰洋系统配送请求失败"));
    }
}
