/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.server.domain;
import com.github.vvorks.builder.shared.common.lang.Encodable;

/**
 * レイアウト種別
 */
public enum LayoutType implements Encodable<String> {

	/**
	 * 基本領域
	 *
	 * 複数レイアウトを位置指定通り配置（スクロール可）
	 */
	SIMPLE_PANE(10, "SIMPLE_PANE"),

	/**
	 * 分割領域
	 *
	 * 複数レイアウトを上下左右に分割配置
	 */
	PARTED_PANE(11, "PARTED_PANE"),

	/**
	 * タブ領域
	 *
	 * 複数レイアウトをタブ切り替え可能に配置
	 */
	TABBED_PANE(12, "TABBED_PANE"),

	/**
	 * タブ
	 *
	 * タブ領域切り替え用のタブ
	 */
	TAB(13, "TAB"),

	/**
	 * 垂直リスト
	 *
	 * SETデータをリスト表示する。内部にSET先の項目を定義する。
	 */
	V_LIST(20, "V_LIST"),

	/**
	 * 水平リスト
	 *
	 * SETデータをリスト表示する。内部にSET先の項目を定義する。
	 */
	H_LIST(21, "H_LIST"),

	/**
	 * 垂直スクロールバー
	 *
	 * SIMPLE_LAYOUT又は(V/H)LISTの垂直スクロールバー
	 */
	V_SCROLLBAR(22, "V_SCROLLBAR"),

	/**
	 * 水平スクロールバー
	 *
	 * SIMPLE_LAYOUT又は(V/H)LISTの水平スクロールバー
	 */
	H_SCROLLBAR(23, "H_SCROLLBAR"),

	/**
	 * ラベル表示要素
	 *
	 * cre/eref/frefのラベル部又はmrefを出力する
	 */
	LABEL(31, "LABEL"),

	/**
	 * フィールド出力要素
	 *
	 * frefのデータを出力する
	 */
	FIELD(32, "FIELD"),

	/**
	 * フィールド入力要素
	 *
	 * frefのデータを入出力する
	 */
	INPUT(33, "INPUT")
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
