package com.github.vvorks.builder.server.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumSet;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public abstract class EnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

	private static EnumSet<JdbcType> NUMBER_TYPES = EnumSet.of(
			JdbcType.TINYINT, JdbcType.SMALLINT, JdbcType.INTEGER, JdbcType.BIGINT,
			JdbcType.FLOAT, JdbcType.REAL, JdbcType.DOUBLE,
			JdbcType.NUMERIC, JdbcType.DECIMAL);

	private static EnumSet<JdbcType> STRING_TYPES = EnumSet.of(
			JdbcType.CHAR, JdbcType.VARCHAR, JdbcType.LONGVARCHAR,
			JdbcType.BLOB, JdbcType.CLOB,
			JdbcType.NCHAR, JdbcType.NCLOB);

	@Override
	public void setNonNullParameter(PreparedStatement ps, int index, E parameter, JdbcType jdbcType) throws SQLException {
		if (STRING_TYPES.contains(jdbcType)) {
			ps.setString(index, encodeString(parameter));
		} else if (NUMBER_TYPES.contains(jdbcType)) {
			ps.setInt(index, encodeInt(parameter));
		} else {
			throw new SQLException("type mismatch");
		}
	}

	@Override
	public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return getNullableResult(rs, rs.findColumn(columnName));
	}

	@Override
	public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		JdbcType jdbcType = JdbcType.forCode(rs.getMetaData().getColumnType(columnIndex));
		if (STRING_TYPES.contains(jdbcType)) {
			return decodeString(rs.getString(columnIndex));
		} else if (NUMBER_TYPES.contains(jdbcType)) {
			return decodeInt(rs.getInt(columnIndex));
		} else {
			throw new SQLException("type mismatch");
		}
	}

	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		JdbcType jdbcType = JdbcType.forCode(cs.getMetaData().getColumnType(columnIndex));
		if (STRING_TYPES.contains(jdbcType)) {
			return decodeString(cs.getString(columnIndex));
		} else if (NUMBER_TYPES.contains(jdbcType)) {
			return decodeInt(cs.getInt(columnIndex));
		} else {
			throw new SQLException("type mismatch");
		}
	}

	public abstract String encodeString(E obj);

	public abstract int encodeInt(E obj);

	public abstract E decodeString(String str);

	public abstract E decodeInt(int str);

}
