/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;
import com.github.vvorks.builder.common.lang.Encodable;

/**
 * レイアウトタイプ
 */
public enum LayoutType implements Encodable<String> {

	/**
	 * グループ
	 */
	GROUP(1, "GROUP"),

	/**
	 * フィールドラベル表示
	 */
	SHOW_LABEL(2, "SHOW_LABEL"),

	/**
	 * フィールドデータ表示
	 */
	SHOW_DATA(3, "SHOW_DATA"),

	/**
	 * フィールドデータ編集
	 */
	EDIT_DATA(4, "EDIT_DATA"),

	/**
	 * メッセージ表示
	 */
	SHOW_MESSAGE(5, "SHOW_MESSAGE")
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
	 * LayoutTypeを作成する
	 *
	 * @param code 列挙コード
	 */
	private LayoutType(int code, String _title) {
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
	public static LayoutType valueOf(int value) {
		for (LayoutType e : values()) {
			if (e.code() == value) {
				return e;
			}
		}
		return null;
	}

}
