/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import java.util.Map;
import java.util.LinkedHashMap;
import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.shared.common.json.Json;

/**
 * ページエージェント
 */
public class PageAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final PageAgent INSTANCE = new PageAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static PageAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private PageAgent() {
	}

	@Override
	public String getTypeName() {
		return "Page";
	}

	@Override
	public Json getValue(DataRecord rec, String name) {
		Json data = Json.createObject();
		data.setInt("pageId", rec.getInt(name + "PageId"));
		return data;
	}

	@Override
	public void setValue(DataRecord rec, String name, Json data) {
		rec.setInt(name + "PageId", data.getInt("pageId"));
	}

	@Override
	public Json fromParam(Map<String, String> param) {
		Json key = Json.createObject();
		key.setInt("pageId", asInt(param.get("pageId")));
		return key;
	}

	@Override
	public Map<String, String> toParam(Json data) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("pageId", toString(data.getInt("pageId")));
		return map;
	}

}
