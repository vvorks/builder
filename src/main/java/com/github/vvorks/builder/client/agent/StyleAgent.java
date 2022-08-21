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
 * スタイルエージェント
 */
public class StyleAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final StyleAgent INSTANCE = new StyleAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static StyleAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private StyleAgent() {
	}

	@Override
	public String getTypeName() {
		return "Style";
	}

	@Override
	public Json getValue(DataRecord rec, String name) {
		Json data = Json.createObject();
		data.setInt("styleId", rec.getInt(name + "StyleId"));
		return data;
	}

	@Override
	public void setValue(DataRecord rec, String name, Json data) {
		rec.setInt(name + "StyleId", data.getInt("styleId"));
	}

	@Override
	public Json fromParam(Map<String, String> param) {
		Json key = Json.createObject();
		key.setInt("styleId", asInt(param.get("styleId")));
		return key;
	}

	@Override
	public Map<String, String> toParam(Json data) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("styleId", toString(data.getInt("styleId")));
		return map;
	}

}
