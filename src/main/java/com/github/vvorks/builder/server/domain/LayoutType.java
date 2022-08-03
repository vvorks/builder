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
	 * 基本レイアウト
	 *
	 * 複数レイアウトを位置指定でまとめる●スクロールの仕組みがある
	 */
	BASIC_LAYOUT(10, "BASIC_LAYOUT"),

	/**
	 * 分割レイアウト
	 *
	 * 複数レイアウトを上下左右に配置する●グループ間にサイズ調整用のスプリッタがある
	 */
	SPLIT_LAYOUT(11, "SPLIT_LAYOUT"),

	/**
	 * 重ねレイアウト
	 *
	 * 複数レイアウトを重ねて配置するレイアウト
	 */
	PILED_LAYOUT(12, "PILED_LAYOUT"),

	/**
	 * タブ
	 *
	 * 重ねレイアウトの表示切替用。文言はmessageRefを使用
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
	 * BASIC_LAYOUT又は(V/H)LISTの垂直スクロールバー
	 */
	V_SCROLLBAR(22, "V_SCROLLBAR"),

	/**
	 * 水平スクロールバー
	 *
	 * BASIC_LAYOUT又は(V/H)LISTの水平スクロールバー
	 */
	H_SCROLLBAR(23, "H_SCROLLBAR"),

	/**
	 * メッセージ表示要素
	 *
	 * messageRefを出力する
	 */
	MESSAGE(30, "MESSAGE"),

	/**
	 * ラベル表示要素
	 *
	 * fieldRefのラベル部を出力する
	 */
	LABEL(31, "LABEL"),

	/**
	 * フィールド出力要素
	 *
	 * fieldRefのデータを出力する
	 */
	FIELD(32, "FIELD"),

	/**
	 * フィールド入力要素
	 *
	 * fieldRefのデータを入出力する
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
