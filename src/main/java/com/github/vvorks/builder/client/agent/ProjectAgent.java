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
 * プロジェクトエージェント
 */
public class ProjectAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final ProjectAgent INSTANCE = new ProjectAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static ProjectAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private ProjectAgent() {
	}

	@Override
	public String getTypeName() {
		return "Project";
	}

	@Override
	public Json getValue(DataRecord rec, String name) {
		Json data = Json.createObject();
		data.setInt("projectId", rec.getInt(name + "ProjectId"));
		return data;
	}

	@Override
	public void setValue(DataRecord rec, String name, Json data) {
		rec.setInt(name + "ProjectId", data.getInt("projectId"));
	}

	@Override
	public Json fromParam(Map<String, String> param) {
		Json key = Json.createObject();
		key.setInt("projectId", asInt(param.get("projectId")));
		return key;
	}

	@Override
	public Map<String, String> toParam(Json data) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("projectId", toString(data.getInt("projectId")));
		return map;
	}

}
