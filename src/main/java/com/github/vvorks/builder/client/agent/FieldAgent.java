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
 * フィールドエージェント
 */
public class FieldAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final FieldAgent INSTANCE = new FieldAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static FieldAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private FieldAgent() {
	}

	@Override
	public String getTypeName() {
		return "Field";
	}

	@Override
	public Json getValue(DataRecord rec, String name) {
		Json data = Json.createObject();
		data.setInt("fieldId", rec.getInt(name + "FieldId"));
		return data;
	}

	@Override
	public void setValue(DataRecord rec, String name, Json data) {
		rec.setInt(name + "FieldId", data.getInt("fieldId"));
	}

	@Override
	public Json fromParam(Map<String, String> param) {
		Json key = Json.createObject();
		key.setInt("fieldId", asInt(param.get("fieldId")));
		return key;
	}

	@Override
	public Map<String, String> toParam(Json data) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("fieldId", toString(data.getInt("fieldId")));
		return map;
	}

}
