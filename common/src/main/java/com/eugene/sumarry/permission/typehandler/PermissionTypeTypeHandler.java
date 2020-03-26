package com.eugene.sumarry.permission.typehandler;

import com.eugene.sumarry.permission.Enum.PermissionType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PermissionTypeTypeHandler extends BaseTypeHandler<PermissionType> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, PermissionType parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public PermissionType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int enumValue = rs.getInt(columnName);
        return PermissionType.int2Enum(enumValue);
    }

    @Override
    public PermissionType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int enumValue = rs.getInt(columnIndex);
        return PermissionType.int2Enum(enumValue);
    }

    @Override
    public PermissionType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int enumValue = cs.getInt(columnIndex);
        return PermissionType.int2Enum(enumValue);
    }
}
