package com.hlqz.lpg.service;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.hlqz.lpg.easyexcel.data.CylinderExcelData;
import com.hlqz.lpg.easyexcel.listener.CylinderExcelReadListener;
import com.hlqz.lpg.lanyang.service.LyService;
import com.hlqz.lpg.model.convert.CylinderConvert;
import com.hlqz.lpg.model.dto.CylinderSearchDTO;
import com.hlqz.lpg.model.dto.CylinderUploadDTO;
import com.hlqz.lpg.model.entity.Cylinder;
import com.hlqz.lpg.model.enums.RcEnum;
import com.hlqz.lpg.model.vo.CylinderSearchVO;
import com.hlqz.lpg.model.vo.CylinderUploadVO;
import com.hlqz.lpg.mybatis.dao.CylinderDAO;
import com.hlqz.lpg.util.AssertionUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author Karbob
 * @date 2024-01-09
 */
@Slf4j
@Service
public class CylinderService {

    @Resource
    private CylinderDAO cylinderDAO;
    @Resource
    private LyService lyService;

    public List<CylinderSearchVO> search(CylinderSearchDTO dto) {
        final var barcode = dto.getBarcode().trim().toUpperCase();
        final var queryPage = new Page<Cylinder>(1, 5);
        final var result = cylinderDAO.fetchBarcodePageUsingLike(barcode, queryPage);
        final List<Cylinder> cylinderList = result.getRecords();
        return CylinderConvert.toVO(cylinderList);
    }

    public List<CylinderUploadVO> importFromExcel(CylinderUploadDTO dto) {
        final var files = dto.getFiles();
        final var repair = dto.getRepair();
        // 限制上传的文件个数
        AssertionUtils.assertTrue(files.size() <= 5, "一次上传的文件个数不能超过【5】个");
        final List<CylinderUploadVO> voList = Lists.newArrayListWithExpectedSize(files.size());
        for (var file : files) {
            final var vo = new CylinderUploadVO();
            final var fileName = file.getOriginalFilename();
            vo.setFileName(fileName);
            voList.add(vo);
            // 目前只允许 .xls, .xlsx 文件
            if (Objects.isNull(fileName) || !StringUtils.endsWithAny(fileName, ".xls", ".xlsx")) {
                vo.setSuccess(false);
                vo.setMessage("请上传正确的文件格式");
                continue;
            }
            // 处理 Excel
            final var listener = new CylinderExcelReadListener(cylinderDAO, lyService, repair);
            try {
                EasyExcel.read(file.getInputStream(), CylinderExcelData.class, listener)
                    .sheet()
                    .doRead();
            } catch (Exception e) {
                log.error("CylinderService, 文件处理异常, fileName: {}", fileName, e);
                vo.setSuccess(false);
                vo.setMessage("文件处理失败");
                listener.destroy();
                continue;
            }
            // 不一致的钢印号或气瓶条码
            final var diffList = listener.getDiffList();
            listener.destroy();
            vo.setDiffList(diffList);
            vo.setSuccess(true);
            vo.setMessage(CollectionUtils.isEmpty(diffList) ? RcEnum.OK.getMessage() : "存在不一致的钢印号或气瓶条码");
        }
        return voList;
    }
}
