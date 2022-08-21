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
 * クラスエージェント
 */
public class ClassAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final ClassAgent INSTANCE = new ClassAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static ClassAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private ClassAgent() {
	}

	@Override
	public String getTypeName() {
		return "Class";
	}

	@Override
	public Json getValue(DataRecord rec, String name) {
		Json data = Json.createObject();
		data.setInt("classId", rec.getInt(name + "ClassId"));
		return data;
	}

	@Override
	public void setValue(DataRecord rec, String name, Json data) {
		rec.setInt(name + "ClassId", data.getInt("classId"));
	}

	@Override
	public Json fromParam(Map<String, String> param) {
		Json key = Json.createObject();
		key.setInt("classId", asInt(param.get("classId")));
		return key;
	}

	@Override
	public Map<String, String> toParam(Json data) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("classId", toString(data.getInt("classId")));
		return map;
	}

}
