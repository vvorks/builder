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
 * ページセットエージェント
 */
public class PageSetAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final PageSetAgent INSTANCE = new PageSetAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static PageSetAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private PageSetAgent() {
	}

	@Override
	public String getTypeName() {
		return "PageSet";
	}

	@Override
	public Json getValue(DataRecord rec, String name) {
		Json data = Json.createObject();
		data.setInt("pageSetId", rec.getInt(name + "PageSetId"));
		return data;
	}

	@Override
	public void setValue(DataRecord rec, String name, Json data) {
		rec.setInt(name + "PageSetId", data.getInt("pageSetId"));
	}

	@Override
	public Json fromParam(Map<String, String> param) {
		Json key = Json.createObject();
		key.setInt("pageSetId", asInt(param.get("pageSetId")));
		return key;
	}

	@Override
	public Map<String, String> toParam(Json data) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("pageSetId", toString(data.getInt("pageSetId")));
		return map;
	}

}
