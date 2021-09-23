package com.github.vvorks.builder.server.domain;

public enum DataType {

	KEY(10),

	CLASS_REF(11),

	ENUM_REF(12),

	FIELD_REF(13),

	INVERT_REF(14),

	/**真偽値*/
	BOOLEAN(20),

	/**整数*/
	INTEGER(30),

	/**浮動小数*/
	REAL(40),

	/**１０進小数*/
	NUMERIC(50),

	/**日付・時刻*/
	DATE(60),

	/**文字列*/
	STRING(70);

	private final int code;

	private DataType(int code) {
		this.code = code;
	}

	public int code() {
		return code;
	}

	public static DataType valueOf(int value) {
		for (DataType t : values()) {
			if (t.code() == value) {
				return t;
			}
		}
		return null;
	}

}
