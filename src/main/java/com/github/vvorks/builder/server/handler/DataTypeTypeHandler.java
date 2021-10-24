/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.github.vvorks.builder.server.domain.DataType;

/**
 * データ型のタイプハンドラ
 */
public class DataTypeTypeHandler extends BaseTypeHandler<DataType> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int index, DataType parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(index, parameter.name());
	}

	@Override
	public DataType getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return getNullableResult(rs, rs.findColumn(columnName));
	}

	@Override
	public DataType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return DataType.valueOf(rs.getString(columnIndex));
	}

	@Override
	public DataType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return DataType.valueOf(cs.getString(columnIndex));
	}

}
