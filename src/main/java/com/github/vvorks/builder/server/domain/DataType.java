/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

/**
 * データ型
 */
public enum DataType {

	KEY(10),
	CLASS_REF(11),
	ENUM_REF(12),
	FIELD_REF(13),
	INVERT_REF(14),
	BOOLEAN(20),
	INTEGER(30),
	REAL(40),
	NUMERIC(50),
	DATE(60),
	STRING(70)
	;

	private final int code;

	private DataType(int code) {
		this.code = code;
	}

	public int code() {
		return this.code;
	}

	public static DataType valueOf(int value) {
		for (DataType e : values()) {
			if (e.code() == value) {
				return e;
			}
		}
		return null;
	}

}
