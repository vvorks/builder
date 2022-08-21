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
 * クエリーエージェント
 */
public class QueryAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final QueryAgent INSTANCE = new QueryAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static QueryAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private QueryAgent() {
	}

	@Override
	public String getTypeName() {
		return "Query";
	}

	@Override
	public Json getValue(DataRecord rec, String name) {
		Json data = Json.createObject();
		data.setInt("queryId", rec.getInt(name + "QueryId"));
		return data;
	}

	@Override
	public void setValue(DataRecord rec, String name, Json data) {
		rec.setInt(name + "QueryId", data.getInt("queryId"));
	}

	@Override
	public Json fromParam(Map<String, String> param) {
		Json key = Json.createObject();
		key.setInt("queryId", asInt(param.get("queryId")));
		return key;
	}

	@Override
	public Map<String, String> toParam(Json data) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("queryId", toString(data.getInt("queryId")));
		return map;
	}

}
