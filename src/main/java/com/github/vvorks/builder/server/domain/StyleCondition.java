/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;
import com.github.vvorks.builder.common.lang.Encodable;

/**
 * スタイル条件
 */
public enum StyleCondition implements Encodable<String> {

	/**
	 * 基本状態
	 */
	DEFAULT(1, "DEFAULT"),

	/**
	 * 有効状態
	 */
	ENABLE(2, "ENABLE"),

	/**
	 * 無効状態
	 */
	DISABLE(3, "DISABLE"),

	/**
	 * フォーカス状態
	 */
	FOCUS(4, "FOCUS"),

	/**
	 * クリック中
	 */
	CLICKING(5, "CLICKING"),

	/**
	 * UIノード名限定
	 */
	NAMED(6, "NAMED")
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
	 * StyleConditionを作成する
	 *
	 * @param code 列挙コード
	 */
	private StyleCondition(int code, String _title) {
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
	public static StyleCondition valueOf(int value) {
		for (StyleCondition e : values()) {
			if (e.code() == value) {
				return e;
			}
		}
		return null;
	}

}
