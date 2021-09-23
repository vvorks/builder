package com.github.vvorks.builder.server.handler;

import com.github.vvorks.builder.server.domain.DataType;

public class DataTypeTypeHandler extends EnumTypeHandler<DataType> {

	@Override
	public String encodeString(DataType obj) {
		return obj.name();
	}

	@Override
	public int encodeInt(DataType obj) {
		return obj.code();
	}

	@Override
	public DataType decodeString(String value) {
		return DataType.valueOf(value);
	}

	@Override
	public DataType decodeInt(int value) {
		return DataType.valueOf(value);
	}

}
