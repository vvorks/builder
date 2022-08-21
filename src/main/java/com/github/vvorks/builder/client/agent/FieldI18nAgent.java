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
 * フィールド(I18n)エージェント
 */
public class FieldI18nAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final FieldI18nAgent INSTANCE = new FieldI18nAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static FieldI18nAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private FieldI18nAgent() {
	}

	@Override
	public String getTypeName() {
		return "FieldI18n";
	}

	@Override
	public Json getValue(DataRecord rec, String name) {
		Json data = Json.createObject();
		data.setInt("ownerFieldId", rec.getInt(name + "OwnerFieldId"));
		data.setString("targetLocaleId", rec.getString(name + "TargetLocaleId"));
		return data;
	}

	@Override
	public void setValue(DataRecord rec, String name, Json data) {
		rec.setInt(name + "OwnerFieldId", data.getInt("ownerFieldId"));
		rec.setString(name + "TargetLocaleId", data.getString("targetLocaleId"));
	}

	@Override
	public Json fromParam(Map<String, String> param) {
		Json key = Json.createObject();
		key.setInt("ownerFieldId", asInt(param.get("ownerFieldId")));
		key.setString("targetLocaleId", param.get("targetLocaleId"));
		return key;
	}

	@Override
	public Map<String, String> toParam(Json data) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("ownerFieldId", toString(data.getInt("ownerFieldId")));
		map.put("targetLocaleId", data.getString("targetLocaleId"));
		return map;
	}

}
