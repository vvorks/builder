package com.github.vvorks.builder.client.gwt.intl;

import com.github.vvorks.builder.client.gwt.util.JsObject;

/**
 * オプション
 *
 * @param <T> データ型
 */
public interface Option<T> {

	/**
	 * オプションキー名を取得する
	 *
	 * @return オプションキー名
	 */
	public String key();

	/**
	 * オプション値を取得する
	 *
	 * @return オプション値
	 */
	public T value();

	/**
	 * オプション値を指定されたオブジェクトに設定する
	 *
	 * @param options 設定先オブジェクト
	 */
	public void setInto(JsObject options);

}
