/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;

/**
 * データ型
 */
public enum DataType {

	/**
	 * （自動）キー
	 */
	KEY(10),

	/**
	 * クラス参照型
	 */
	REF(11),

	/**
	 * 列挙参照型
	 */
	ENUM(12),

	/**
	 * 集合（被参照）型
	 */
	SET(13),

	/**
	 * 列挙値型
	 *
	 * 内部用。モデル定義での使用不可
	 */
	ENUM_VALUE(14),

	/**
	 * 真偽値
	 */
	BOOLEAN(20),

	/**
	 * 整数
	 */
	INTEGER(30),

	/**
	 * 浮動小数
	 */
	REAL(40),

	/**
	 * １０進小数
	 */
	NUMERIC(50),

	/**
	 * 日付・時刻
	 */
	DATE(60),

	/**
	 * 文字列
	 */
	STRING(70)
	;

	/**
	 * 列挙コード
	 */
	private final int code;

	/**
	 * DataTypeを作成する
	 *
	 * @param code 列挙コード
	 */
	private DataType(int code) {
		this.code = code;
	}

	/**
	 * 列挙コードを取得する
	 */
	public int code() {
		return this.code;
	}

	/**
	 * 列挙コードから列挙オブジェクトを取得する
	 */
	public static DataType valueOf(int value) {
		for (DataType e : values()) {
			if (e.code() == value) {
				return e;
			}
		}
		return null;
	}

}
