package com.hlqz.lpg.mybatis.handler;

import com.hlqz.lpg.util.AesUtils;
import com.hlqz.lpg.util.ConfigUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;
import java.util.Objects;

/**
 * @author Karbob
 * @date 2024-01-06
 */
public class CryptoTypeHandler extends BaseTypeHandler<String> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        if (Objects.isNull(parameter)) {
            ps.setNull(i, Types.VARCHAR);
            return;
        }
        ps.setString(i, AesUtils.encrypt(parameter, ConfigUtils.getDatabaseAesKey()));
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String result = rs.getString(columnName);
        if (Objects.isNull(result)) {
            return null;
        }
        return AesUtils.decrypt(result, ConfigUtils.getDatabaseAesKey());
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String result = rs.getString(columnIndex);
        if (Objects.isNull(result)) {
            return null;
        }
        return AesUtils.decrypt(result, ConfigUtils.getDatabaseAesKey());
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String result = cs.getString(columnIndex);
        if (Objects.isNull(result)) {
            return null;
        }
        return AesUtils.decrypt(result, ConfigUtils.getDatabaseAesKey());
    }
}
