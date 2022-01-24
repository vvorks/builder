package com.github.vvorks.builder.common.util;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.lang.Factory;

public interface JsonResourceBundle {

	public static JsonResourceBundle getBundle() {
		return Factory.getInstance(JsonResourceBundle.class);
	}

	/**
	 * ロケールを取得する
	 *
	 * @return ロケール
	 */
	public String getLocale();

	/**
	 * JSONリソースを取得する
	 *
	 * @param resourceName リソース名
	 * @return リソースJSONオブジェクト
	 */
	public Json getResource(String resourceName);

}
