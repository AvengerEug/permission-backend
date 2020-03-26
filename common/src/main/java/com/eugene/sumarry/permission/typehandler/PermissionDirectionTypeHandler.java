package com.eugene.sumarry.permission.typehandler;

import com.eugene.sumarry.permission.Enum.PermissionDirection;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PermissionDirectionTypeHandler extends BaseTypeHandler<PermissionDirection> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, PermissionDirection parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public PermissionDirection getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int enumValue = rs.getInt(columnName);
        return PermissionDirection.int2Enum(enumValue);
    }

    @Override
    public PermissionDirection getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int enumValue = rs.getInt(columnIndex);
        return PermissionDirection.int2Enum(enumValue);
    }

    @Override
    public PermissionDirection getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int enumValue = cs.getInt(columnIndex);
        return PermissionDirection.int2Enum(enumValue);
    }
}
