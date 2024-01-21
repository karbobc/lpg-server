package com.hlqz.lpg.lanyang.helper;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import com.hlqz.lpg.lanyang.model.dto.LyDeliveryDTO;
import com.hlqz.lpg.lanyang.model.enums.LyOrderTypeEnum;
import com.hlqz.lpg.lanyang.model.request.LyDeliveryParam;
import com.hlqz.lpg.lanyang.model.request.LyFetchByPageParam;
import com.hlqz.lpg.util.ConfigUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * @author Karbob
 * @date 2024-01-21
 */
public class LyHelper {

    private LyHelper() {
    }

    public static LyFetchByPageParam buildCylinderPageParam() {
        final var param = new LyFetchByPageParam();
        param.setPage(1);
        param.setSize(20);
        param.setSqlWhere(StringUtils.EMPTY);
        param.setOrderType(LyOrderTypeEnum.DESC);
        param.setOrderBy("UpdTime");
        return param;
    }

    public static LyFetchByPageParam buildCustomerPageParam() {
        final var param = new LyFetchByPageParam();
        param.setPage(1);
        param.setSize(20);
        param.setSqlWhere(StringUtils.EMPTY);
        param.setOrderType(LyOrderTypeEnum.DESC);
        param.setOrderBy("EditTime");
        return param;
    }

    public static LyDeliveryParam buildDeliveryParam(LyDeliveryDTO dto) {
        final var param = new LyDeliveryParam();
        final var customer = dto.getCustomer();
        final var barcodeList = dto.getBarcodeList();
        final var deliveryDate = dto.getDeliveryDate();
        param.setCrNo(customer.getCrNo());
        param.setCrName(customer.getCrName());
        param.setBarcode(StringUtils.join(barcodeList, ","));
        param.setSectionId(Long.valueOf(ConfigUtils.getLySectionId()));
        param.setOpId(Long.valueOf(ConfigUtils.getLyOptId()));
        param.setRemark("手持终端创建");
        param.setSyncTab(1);
        param.setDeliveryDate(deliveryDate);
        param.setSerialNo(IdUtil.fastSimpleUUID());
        return param;
    }

    public static String buildEncryptedSqlWhere(String sqlWhere) {
        return Base64.encode(sqlWhere);
    }

    public static String buildSqlWhereForIn(String column, Collection<?> fields) {
        if (CollectionUtils.isEmpty(fields)) {
            return StringUtils.EMPTY;
        }
        final var sb = new StringBuilder();
        sb.append(column)
            .append(" IN (");
        for (var field : fields) {
            sb.append("'")
                .append(field)
                .append("',");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        return sb.toString();
    }

    public static String buildSqlWhereForEq(String column, Object value) {
        return StringUtils.join(column, " = '", value, "'");
    }

    public static String appendSqlWhereForEq(String column, Object value, String keyword) {
        return StringUtils.join(" ", keyword, " ", column, " = '", value, "'");
    }
}
