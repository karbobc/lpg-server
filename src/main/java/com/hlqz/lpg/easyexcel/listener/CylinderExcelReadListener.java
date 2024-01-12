package com.hlqz.lpg.easyexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hlqz.lpg.easyexcel.data.CylinderExcelData;
import com.hlqz.lpg.model.entity.Cylinder;
import com.hlqz.lpg.model.vo.CylinderDiffVO;
import com.hlqz.lpg.mybatis.dao.CylinderDAO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Karbob
 * @date 2024-01-11
 */
@Slf4j
public class CylinderExcelReadListener implements ReadListener<CylinderExcelData> {

    /**
     * 批次入库个数, 分批次入库, 防止内存过高
     */
    private static final int BATCH_COUNT = 20;

    /**
     * 缓存批次数据
     */
    private static final List<Cylinder> CACHED_LIST = Lists.newArrayListWithExpectedSize(BATCH_COUNT);

    /**
     * 缓存批次中钢印号和气瓶条码映射数据, 一条数据存储两个映射关系, serialNo -> barcode, barcode -> serialNo
     */
    private static final Map<String, String> CACHED_MAP = Maps.newHashMapWithExpectedSize(BATCH_COUNT * 2);

    /**
     * 缓存Excel与数据库不一致数据, 最终需要调用 destroy 方法进行回收
     */
    private static final List<CylinderDiffVO> DIFF_LIST = Lists.newArrayList();

    private CylinderDAO cylinderDAO;

    private CylinderExcelReadListener() {
    }

    public CylinderExcelReadListener(CylinderDAO cylinderDAO) {
        this.cylinderDAO = cylinderDAO;
    }

    @Override
    public void invoke(CylinderExcelData data, AnalysisContext context) {
        final var serialNo = data.getSerialNo().trim();
        final var barcode = data.getBarcode().trim();
        final var cachedSerialNo = CACHED_MAP.get(barcode);
        final var cachedBarcode = CACHED_MAP.get(serialNo);
        data.setSerialNo(serialNo);
        data.setBarcode(barcode);
        // 避免重复入库, 入库前校验
        if (ObjectUtils.anyNotNull(cachedSerialNo, cachedBarcode)) {
            final var rowIndex = context.readRowHolder().getRowIndex();
            // 如果 Excel 中数据跟批次缓存中的数据一致, 跳过这条数据
            if (StringUtils.equals(serialNo, cachedSerialNo) && StringUtils.equals(barcode, cachedBarcode)) {
                log.info("CylinderExcelReadListener, 重复钢瓶数据, serialNo: {}, barcode: {}, index: {}", serialNo, barcode, rowIndex);
                return;
            }
            log.info("CylinderExcelReadListener, 不一致钢瓶数据, serialNo1: {}, barcode1: {}, serialNo2: {}, barcode2: {}, index: {}",
                serialNo, barcode, cachedSerialNo, cachedBarcode, rowIndex);
            DIFF_LIST.add(buildDiffVO(serialNo, barcode, cachedSerialNo, cachedBarcode));
            return;
        }
        final var entity = cylinderDAO.fetchBySerialNoOrBarcode(serialNo, barcode);
        if (Objects.nonNull(entity)) {
            // 如果 Excel 中数据跟数据库中数据一致, 跳过这条数据
            if (StringUtils.equals(serialNo, entity.getSerialNo()) && StringUtils.equals(barcode, entity.getBarcode())) {
                return;
            }
            final var rowIndex = context.readRowHolder().getRowIndex();
            log.info("CylinderExcelReadListener, 不一致钢瓶数据, data: {}, entity: {}, index: {}", data, entity, rowIndex);
            DIFF_LIST.add(buildDiffVO(data, entity));
            return;
        }
        CACHED_LIST.add(convertToEntity(data));
        CACHED_MAP.put(serialNo, barcode);
        CACHED_MAP.put(barcode, serialNo);
        // 分批次入库
        if (CACHED_LIST.size() == BATCH_COUNT) {
            cylinderDAO.saveBatch(CACHED_LIST);
            CACHED_LIST.clear();
            CACHED_MAP.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (CollectionUtils.isNotEmpty(CACHED_LIST)) {
            cylinderDAO.saveBatch(CACHED_LIST);
            CACHED_LIST.clear();
            CACHED_MAP.clear();
        }
        final var count = context.readRowHolder().getRowIndex();
        log.info("CylinderExcelReadListener, 文件处理完成, count: {}", count);
    }

    public List<CylinderDiffVO> getDiffList() {
        return List.copyOf(DIFF_LIST);
    }

    /**
     * 清空 List, Map
     */
    public void destroy() {
        if (CollectionUtils.isNotEmpty(CACHED_LIST)) {
            CACHED_LIST.clear();
        }
        if (MapUtils.isNotEmpty(CACHED_MAP)) {
            CACHED_MAP.clear();
        }
        if (CollectionUtils.isNotEmpty(DIFF_LIST)) {
            DIFF_LIST.clear();
        }
    }

    /**
     * 构建 CylinderDiffVO
     */
    private CylinderDiffVO buildDiffVO(CylinderExcelData data, Cylinder entity) {
        final var vo = new CylinderDiffVO();
        vo.setSerialNo1(data.getSerialNo());
        vo.setBarcode1(data.getBarcode());
        vo.setSerialNo2(entity.getSerialNo());
        vo.setBarcode2(entity.getBarcode());
        return vo;
    }

    /**
     * 构建 CylinderDiffVO
     */
    private CylinderDiffVO buildDiffVO(String serialNo1, String barcode1, String serialNo2, String barcode2) {
        final var vo = new CylinderDiffVO();
        vo.setSerialNo1(serialNo1);
        vo.setBarcode1(barcode1);
        vo.setSerialNo2(serialNo2);
        vo.setBarcode2(barcode2);
        return vo;
    }

    /**
     * 将 ExcelData 对象转为数据库对象
     */
    private Cylinder convertToEntity(CylinderExcelData data) {
        final var entity = new Cylinder();
        entity.setSerialNo(data.getSerialNo().trim());
        entity.setBarcode(data.getBarcode().trim());
        return entity;
    }
}
