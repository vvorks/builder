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
 * プロジェクト(I18n)エージェント
 */
public class ProjectI18nAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final ProjectI18nAgent INSTANCE = new ProjectI18nAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static ProjectI18nAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private ProjectI18nAgent() {
	}

	@Override
	public String getTypeName() {
		return "ProjectI18n";
	}

	@Override
	public Json getValue(DataRecord rec, String name) {
		Json data = Json.createObject();
		data.setInt("ownerProjectId", rec.getInt(name + "OwnerProjectId"));
		data.setString("targetLocaleId", rec.getString(name + "TargetLocaleId"));
		return data;
	}

	@Override
	public void setValue(DataRecord rec, String name, Json data) {
		rec.setInt(name + "OwnerProjectId", data.getInt("ownerProjectId"));
		rec.setString(name + "TargetLocaleId", data.getString("targetLocaleId"));
	}

	@Override
	public Json fromParam(Map<String, String> param) {
		Json key = Json.createObject();
		key.setInt("ownerProjectId", asInt(param.get("ownerProjectId")));
		key.setString("targetLocaleId", param.get("targetLocaleId"));
		return key;
	}

	@Override
	public Map<String, String> toParam(Json data) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("ownerProjectId", toString(data.getInt("ownerProjectId")));
		map.put("targetLocaleId", data.getString("targetLocaleId"));
		return map;
	}

}
