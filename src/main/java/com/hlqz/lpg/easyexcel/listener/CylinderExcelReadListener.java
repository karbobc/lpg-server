package com.hlqz.lpg.easyexcel.listener;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hlqz.lpg.easyexcel.data.CylinderExcelData;
import com.hlqz.lpg.lanyang.model.common.LyCylinder;
import com.hlqz.lpg.lanyang.service.LyService;
import com.hlqz.lpg.model.entity.Cylinder;
import com.hlqz.lpg.model.vo.CylinderDiffVO;
import com.hlqz.lpg.mybatis.dao.CylinderRepository;
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

    private CylinderRepository cylinderRepository;
    private LyService lyService;

    /**
     * 如果为 true, 根据兰洋系统接口, 自动纠正数据库中重复数据
     */
    private Boolean repair;

    private CylinderExcelReadListener() {
    }

    public CylinderExcelReadListener(CylinderRepository cylinderRepository, LyService lyService, Boolean repair) {
        this.cylinderRepository = cylinderRepository;
        this.lyService = lyService;
        this.repair = repair;
    }

    @Override
    public void invoke(CylinderExcelData data, AnalysisContext context) {
        var serialNo = StrUtil.cleanBlank(data.getSerialNo());
        var barcode = StrUtil.cleanBlank(data.getBarcode());
        var cachedSerialNo = CACHED_MAP.get(barcode);
        var cachedBarcode = CACHED_MAP.get(serialNo);
        final var rowIndex = context.readRowHolder().getRowIndex();
        // 避免重复入库, 入库前校验
        while (ObjectUtils.anyNotNull(cachedSerialNo, cachedBarcode)) {
            // 如果 Excel 中数据跟批次缓存中的数据一致, 跳过这条数据
            if (StringUtils.equals(serialNo, cachedSerialNo) && StringUtils.equals(barcode, cachedBarcode)) {
                log.info("CylinderExcelReadListener, 重复钢瓶数据, serialNo: {}, barcode: {}, index: {}", serialNo, barcode, rowIndex);
                return;
            }
            // 没有指定需要纠正数据
            final var diffVO = buildDiffVO(serialNo, barcode, cachedSerialNo, cachedBarcode);
            if (ObjectUtils.notEqual(repair, true)) {
                log.info("CylinderExcelReadListener, 钢瓶数据不一致, serialNo1: {}, barcode1: {}, serialNo2: {}, barcode2: {}, index: {}",
                    serialNo, barcode, cachedSerialNo, cachedBarcode, rowIndex);
                DIFF_LIST.add(diffVO);
                return;
            }
            final var repaired = repairData(diffVO);
            // 数据纠正失败
            if (!repaired) {
                log.info("CylinderExcelReadListener, 钢瓶数据不一致, serialNo1: {}, barcode1: {}, serialNo2: {}, barcode2: {}, index: {}",
                    serialNo, barcode, cachedSerialNo, cachedBarcode, rowIndex);
                DIFF_LIST.add(diffVO);
                return;
            }
            // 数据纠正成功, 需要将纠正成功的数据与 cache map 中再次进行比对
            final var repairedData = !StringUtils.equals(serialNo, diffVO.getSerialNo1()) || !StringUtils.equals(barcode, diffVO.getBarcode1());
            final var repairedCache = !StringUtils.equals(cachedSerialNo, diffVO.getSerialNo2()) || !StringUtils.equals(cachedBarcode, diffVO.getBarcode2());
            // 纠正成功的可能有, 当前行的数据和 cache map 中的数据都被纠正, 或者只有其中一个被纠正
            if (repairedCache) {
                final var repairedSerialNo = diffVO.getSerialNo2();
                final var repairedBarcode = diffVO.getBarcode2();
                CACHED_MAP.remove(cachedBarcode);
                CACHED_MAP.remove(cachedSerialNo);
                // 纠正后的数据, 跟 cache map 中重复, 暂时将这条数据剔除
                if (CACHED_MAP.containsKey(repairedSerialNo) || CACHED_MAP.containsKey(repairedBarcode)) {
                    log.warn("CylinderExcelReadListener, 已纠正数据跟同一批次数据冲突, serialNo1: {}, barcode1: {}, serialNo2: {}, barcode2: {}",
                        repairedSerialNo, repairedBarcode, CACHED_MAP.get(repairedBarcode), CACHED_MAP.get(repairedSerialNo));
                    // 保证只有 cache map 中数据被纠正时候, 能够正常退出循环
                    cachedSerialNo = null;
                    cachedBarcode = null;
                } else {
                    // 纠正批次中的数据
                    CACHED_MAP.put(repairedSerialNo, repairedBarcode);
                    CACHED_MAP.put(repairedBarcode, repairedSerialNo);
                    for (var cacheData : CACHED_LIST) {
                        if (StringUtils.equals(cacheData.getSerialNo(), cachedSerialNo)
                            && StringUtils.equals(cacheData.getBarcode(), cachedBarcode)) {
                            cacheData.setSerialNo(repairedSerialNo);
                            cacheData.setBarcode(repairedBarcode);
                            break;
                        }
                    }
                }
            }
            if (repairedData) {
                serialNo = diffVO.getSerialNo1();
                barcode = diffVO.getBarcode1();
                cachedSerialNo = CACHED_MAP.get(barcode);
                cachedBarcode = CACHED_MAP.get(serialNo);
            }
        }
        // 如果是气瓶条码重复, 跳过这条数据, 可能存在纠正后的数据气瓶条码重复, 目前不处理, 待后续人工处理
        if (cylinderRepository.existsByBarcode(barcode)) {
            return;
        }
        var entity = cylinderRepository.fetchBySerialNo(serialNo);
        while (Objects.nonNull(entity)) {
            var entitySerialNo = entity.getSerialNo();
            var entityBarcode = entity.getBarcode();
            data.setSerialNo(serialNo);
            data.setBarcode(barcode);
            // 如果 Excel 中数据跟数据库中数据一致, 跳过这条数据
            if (StringUtils.equals(serialNo, entitySerialNo) && StringUtils.equals(barcode, entityBarcode)) {
                return;
            }
            // 没有指定需要纠正数据
            final var diffVO = buildDiffVO(data, entity);
            if (ObjectUtils.notEqual(repair, true)) {
                log.info("CylinderExcelReadListener, 钢瓶数据不一致, data: {}, entity: {}, index: {}", data, entity, rowIndex);
                DIFF_LIST.add(diffVO);
                return;
            }
            final var repaired = repairData(diffVO);
            // 数据纠正失败
            if (!repaired) {
                log.info("CylinderExcelReadListener, 钢瓶数据不一致, data: {}, entity: {}, index: {}", data, entity, rowIndex);
                DIFF_LIST.add(diffVO);
                return;
            }
            // 数据纠正成功, 需要将纠正成功的数据与数据库中数据再次进行比对
            // 条码被纠正, 不去更改对应数据, 只有钢印号被纠正时候才去修改数据库中数据
            final var repairedData = !StringUtils.equals(serialNo, diffVO.getSerialNo1());
            final var repairedEntity = !StringUtils.equals(entitySerialNo, diffVO.getSerialNo2());
            // 纠正成功的可能有, 当前行的数据和数据库中的数据都被纠正, 或者只有其中一个被纠正
            if (repairedEntity) {
                entity.setSerialNo(diffVO.getSerialNo2());
                cylinderRepository.updateById(entity);
                // 保证只有数据库中数据被纠正时, 能够退出循环
                entity = null;
            }
            if (repairedData) {
                serialNo = diffVO.getSerialNo1();
                entity = cylinderRepository.fetchBySerialNo(serialNo);
            }
        }
        data.setSerialNo(serialNo);
        data.setBarcode(barcode);
        CACHED_LIST.add(convertToEntity(data));
        CACHED_MAP.put(serialNo, barcode);
        CACHED_MAP.put(barcode, serialNo);
        // 分批次入库
        if (CACHED_LIST.size() == BATCH_COUNT) {
            cylinderRepository.saveBatch(CACHED_LIST);
            CACHED_LIST.clear();
            CACHED_MAP.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (CollectionUtils.isNotEmpty(CACHED_LIST)) {
            cylinderRepository.saveBatch(CACHED_LIST);
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
     * 纠正重复数据, 纠正后的数据存储在 diffVO
     */
    private boolean repairData(CylinderDiffVO diffVO) {
        var serialNo1 = diffVO.getSerialNo1();
        var serialNo2 = diffVO.getSerialNo2();
        var barcode1 = diffVO.getBarcode1();
        var barcode2 = diffVO.getBarcode2();
        var repaired = false;
        List<LyCylinder> lyCylinderList = null;
        final var serialNoDuplicate = StringUtils.equals(serialNo1, serialNo2);
        final var barcodeDuplicate = StringUtils.equals(barcode1, barcode2);
        try {
            // 钢印号一致, 气瓶条码不一致, 则去兰洋系统匹配条码对应的钢印号进行修正
            if (serialNoDuplicate) {
                lyCylinderList = lyService.fetchCylinderByBarcodes(barcode1, barcode2);
            }
            // 气瓶条码一致, 钢印号不一致, 则去兰洋系统匹配钢印号对应的气瓶条码进行修正
            if (barcodeDuplicate) {
                lyCylinderList = lyService.fetchCylinderBySerialNos(serialNo1, serialNo2);
            }
        } catch (Exception e) {
            log.error("CylinderExcelReadListener, 请求兰洋系统接口出现异常", e);
            return repaired;
        }
        // 兰洋系统没有查询到数据
        if (CollectionUtils.isEmpty(lyCylinderList)) {
            return repaired;
        }
        // 数据可能有 1 条 或 2 条
        // 如果数据只有一条, 那么可能纠正后的数据和原来的一样, 没有变化
        for (var lyCylinder : lyCylinderList) {
            final var lyBarcode = lyCylinder.getBarcode();
            final var lySerialNo = lyCylinder.getSerialNo();
            if (StringUtils.equals(serialNo1, serialNo2)) {
                if (serialNoDuplicate && StringUtils.equals(barcode1, lyBarcode) && !StringUtils.equals(serialNo1, lySerialNo)) {
                    log.info("CylinderExcelReadListener, 钢瓶数据不一致, 已纠正, barcode: {}, serialNo: {} -> {}",
                        barcode1, serialNo1, lySerialNo);
                    serialNo1 = lySerialNo;
                    repaired = true;
                    continue;
                }
                if (serialNoDuplicate && StringUtils.equals(barcode2, lyBarcode) && !StringUtils.equals(serialNo2, lySerialNo)) {
                    log.info("CylinderExcelReadListener, 钢瓶数据不一致, 已纠正, barcode: {}, serialNo: {} -> {}",
                        barcode2, serialNo2, lySerialNo);
                    serialNo2 = lySerialNo;
                    repaired = true;
                    continue;
                }
                if (barcodeDuplicate && StringUtils.equals(serialNo1, lySerialNo) && !StringUtils.equals(barcode1, lyBarcode)) {
                    log.info("CylinderExcelReadListener, 钢瓶数据不一致, 已纠正, serialNo: {}, barcode: {} -> {}",
                        serialNo1, barcode1, lyBarcode);
                    barcode1 = lyBarcode;
                    repaired = true;
                    continue;
                }
                if (barcodeDuplicate && StringUtils.equals(serialNo2, lySerialNo) && !StringUtils.equals(barcode2, lyBarcode)) {
                    log.info("CylinderExcelReadListener, 钢瓶数据不一致, 已纠正, serialNo: {}, barcode: {} -> {}",
                        serialNo2, barcode2, lyBarcode);
                    barcode2 = lyBarcode;
                    repaired = true;
                }
            }
        }
        if (repaired) {
            diffVO.setSerialNo1(serialNo1);
            diffVO.setSerialNo2(serialNo2);
            diffVO.setBarcode1(barcode1);
            diffVO.setBarcode2(barcode2);
        }
        return repaired;
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
