package com.github.vvorks.builder.shared.common.util;

import com.github.vvorks.builder.shared.common.json.Json;
import com.github.vvorks.builder.shared.common.lang.Factory;

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
