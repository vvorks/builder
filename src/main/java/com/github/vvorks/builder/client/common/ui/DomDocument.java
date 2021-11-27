package com.github.vvorks.builder.client.common.ui;

import java.util.Map;

public interface DomDocument {

	/**
	 * スタイルシートを注入する
	 *
	 * @param cls キーとなるクラス
	 * @param cssMap CSS設定値
	 */
	public void injectStyleSheet(Class<?> cls, Map<String, CssStyle> cssMap);

	/**
	 * スタイルシートを除去する
	 *
	 * @param cls キーとなるクラス
	 */
	public void deinjectStyleSheet(Class<?> cls);

	/**
	 * DOM要素を作成する
	 *
	 * @param ns 名前空間文字列
	 * @param tag タグ文字列
	 * @param owner 親要素
	 * @return
	 */
	public DomElement createElement(String ns, String tag, UiNode owner);

	/**
	 * 編集を開始する
	 *
	 * @param owner 編集対象ノード
	 * @param text 初期文字列
	 */
	public void startEditing(UiNode owner, String text);

	/**
	 * 編集を終了する
	 *
	 * @param owner 編集対象ノード
	 * @return 結果文字列
	 */
	public String endEditing(UiNode owner);

}
