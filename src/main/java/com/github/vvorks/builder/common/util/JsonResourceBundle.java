package com.github.vvorks.builder.common.util;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.lang.Factory;

public interface JsonResourceBundle {

	public static JsonResourceBundle getBundle() {
		return Factory.getInstance(JsonResourceBundle.class);
	}

	/**
	 * 現在のロケールを取得する
	 *
	 * @return ロケール
	 */
	public String getLocale();

	/**
	 * ロケールを設定する
	 */
	public void setLocale(String newLocale);

	/**
	 * 設定されたロケールのJSONリソースを取得する
	 *
	 * @param resourceName リソース名
	 * @return リソースJSONオブジェクト
	 */
	public Json getResource(String resourceName);

}
