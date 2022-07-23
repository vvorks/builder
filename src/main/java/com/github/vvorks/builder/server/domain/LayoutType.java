/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;
import com.github.vvorks.builder.common.lang.Encodable;

/**
 * レイアウト種別
 */
public enum LayoutType implements Encodable<String> {

	/**
	 * レイアウト要素
	 *
	 * fieldRef, messageRefは使用しない
	 */
	LAYOUT(10, "LAYOUT"),

	/**
	 * メッセージ表示要素
	 *
	 * messageRefを出力する
	 */
	MESSAGE_OUT(20, "MESSAGE_OUT"),

	/**
	 * (フィールド）ラベル表示要素
	 *
	 * fieldRefのラベルを出力する
	 */
	LABEL_OUT(40, "LABEL_OUT"),

	/**
	 * フィールド出力要素
	 *
	 * fieldRefのデータを出力する
	 */
	FIELD_OUT(50, "FIELD_OUT"),

	/**
	 * フィールド入出力要素
	 *
	 * fieldRefのデータを入出力する
	 */
	FIELD_IO(51, "FIELD_IO")
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
