/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;
import com.github.vvorks.builder.shared.common.lang.Encodable;

/**
 * データ型
 */
public enum DataType implements Encodable<String> {

	/**
	 * （自動）キー
	 */
	KEY(10, "KEY"),

	/**
	 * クラス参照型
	 */
	REF(11, "REF"),

	/**
	 * 列挙参照型
	 */
	ENUM(12, "ENUM"),

	/**
	 * 集合（被参照）型
	 */
	SET(13, "SET"),

	/**
	 * 列挙値型
	 *
	 * 内部用。モデル定義での使用不可
	 */
	ENUM_VALUE(14, "ENUM_VALUE"),

	/**
	 * 真偽値
	 */
	BOOLEAN(20, "BOOLEAN"),

	/**
	 * 整数
	 */
	INTEGER(30, "INTEGER"),

	/**
	 * 浮動小数
	 */
	REAL(40, "REAL"),

	/**
	 * １０進小数
	 */
	NUMERIC(50, "NUMERIC"),

	/**
	 * 日付・時刻
	 */
	DATE(60, "DATE"),

	/**
	 * 文字列
	 */
	STRING(70, "STRING")
	;

	/**
	 * 列挙コード
	 */
	private final int code;

	/**
	 * タイトル
	 */
	private final String _title;

	/**
	 * DataTypeを作成する
	 *
	 * @param code 列挙コード
	 */
	private DataType(int code, String _title) {
		this.code = code;
		this._title = _title;
	}

	/**
	 * 列挙コードを取得する
	 */
	public int code() {
		return this.code;
	}

	@Override
	public String encode() {
		return name();
	}

	public String get_title() {
		return _title;
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
