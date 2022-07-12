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

import com.github.vvorks.builder.server.domain.StyleCondition;

/**
 * スタイル条件のタイプハンドラ
 */
public class StyleConditionTypeHandler extends BaseTypeHandler<StyleCondition> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int index, StyleCondition parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(index, parameter.name());
	}

	@Override
	public StyleCondition getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return getNullableResult(rs, rs.findColumn(columnName));
	}

	@Override
	public StyleCondition getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return StyleCondition.valueOf(rs.getString(columnIndex));
	}

	@Override
	public StyleCondition getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return StyleCondition.valueOf(cs.getString(columnIndex));
	}

}
