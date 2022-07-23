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

import com.github.vvorks.builder.server.domain.LayoutType;

/**
 * レイアウト種別のタイプハンドラ
 */
public class LayoutTypeTypeHandler extends BaseTypeHandler<LayoutType> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int index, LayoutType parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(index, parameter.name());
	}

	@Override
	public LayoutType getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return getNullableResult(rs, rs.findColumn(columnName));
	}

	@Override
	public LayoutType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return LayoutType.valueOf(rs.getString(columnIndex));
	}

	@Override
	public LayoutType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return LayoutType.valueOf(cs.getString(columnIndex));
	}

}
