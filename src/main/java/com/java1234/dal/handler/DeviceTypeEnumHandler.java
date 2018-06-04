package com.java1234.dal.handler;

import com.java1234.dal.enums.DeviceTypeEnum;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 设备平台枚举类型转换器
 *
 * @author lt 2018-6-1
 * @version 1.0.0
 */
public class DeviceTypeEnumHandler implements TypeHandler<DeviceTypeEnum> {

    @Override
    public DeviceTypeEnum getResult(ResultSet rs, String columnName) throws SQLException {
        int type = rs.getInt(columnName);
        return DeviceTypeEnum.getType(type);
    }

    @Override
    public DeviceTypeEnum getResult(ResultSet rs, int columnIndex) throws SQLException {
        int type = rs.getInt(columnIndex);
        return DeviceTypeEnum.getType(type);
    }

    @Override
    public DeviceTypeEnum getResult(CallableStatement state, int columnIndex) throws SQLException {
        int type = state.getInt(columnIndex);
        return DeviceTypeEnum.getType(type);
    }

    @Override
    public void setParameter(PreparedStatement state, int columnIndex, DeviceTypeEnum deviceTypeEnum, JdbcType jdbcType) throws SQLException {
        if (deviceTypeEnum != null) {
            state.setInt(columnIndex, deviceTypeEnum.getValue());
        } else {
            state.setObject(columnIndex, null);
        }
    }

}
