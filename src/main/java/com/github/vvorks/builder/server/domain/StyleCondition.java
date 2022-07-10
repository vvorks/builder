/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;
import com.github.vvorks.builder.common.lang.Encodable;

/**
 * スタイル条件
 */
public enum StyleCondition implements Encodable<Integer> {

	/**
	 * 基本状態
	 */
	BASE(1, "BASE"),

	/**
	 * 無効状態
	 */
	DISABLE(2, "DISABLE"),

	/**
	 * フォーカス状態
	 */
	FOCUS(3, "FOCUS"),

	/**
	 * クリック中
	 */
	CLICKING(4, "CLICKING"),

	/**
	 * UIノード名限定
	 */
	NAMED(5, "NAMED")
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
	public Integer encode() {
		return code();
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
