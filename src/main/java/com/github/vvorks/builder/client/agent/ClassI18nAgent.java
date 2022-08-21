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
 * クラス(I18n)エージェント
 */
public class ClassI18nAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final ClassI18nAgent INSTANCE = new ClassI18nAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static ClassI18nAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private ClassI18nAgent() {
	}

	@Override
	public String getTypeName() {
		return "ClassI18n";
	}

	@Override
	public Json getValue(DataRecord rec, String name) {
		Json data = Json.createObject();
		data.setInt("ownerClassId", rec.getInt(name + "OwnerClassId"));
		data.setString("targetLocaleId", rec.getString(name + "TargetLocaleId"));
		return data;
	}

	@Override
	public void setValue(DataRecord rec, String name, Json data) {
		rec.setInt(name + "OwnerClassId", data.getInt("ownerClassId"));
		rec.setString(name + "TargetLocaleId", data.getString("targetLocaleId"));
	}

	@Override
	public Json fromParam(Map<String, String> param) {
		Json key = Json.createObject();
		key.setInt("ownerClassId", asInt(param.get("ownerClassId")));
		key.setString("targetLocaleId", param.get("targetLocaleId"));
		return key;
	}

	@Override
	public Map<String, String> toParam(Json data) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("ownerClassId", toString(data.getInt("ownerClassId")));
		map.put("targetLocaleId", data.getString("targetLocaleId"));
		return map;
	}

}
