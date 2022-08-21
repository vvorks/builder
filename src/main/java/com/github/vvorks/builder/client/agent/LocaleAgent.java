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
 * ロケールエージェント
 */
public class LocaleAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final LocaleAgent INSTANCE = new LocaleAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static LocaleAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private LocaleAgent() {
	}

	@Override
	public String getTypeName() {
		return "Locale";
	}

	@Override
	public Json getValue(DataRecord rec, String name) {
		Json data = Json.createObject();
		data.setString("localeId", rec.getString(name + "LocaleId"));
		return data;
	}

	@Override
	public void setValue(DataRecord rec, String name, Json data) {
		rec.setString(name + "LocaleId", data.getString("localeId"));
	}

	@Override
	public Json fromParam(Map<String, String> param) {
		Json key = Json.createObject();
		key.setString("localeId", param.get("localeId"));
		return key;
	}

	@Override
	public Map<String, String> toParam(Json data) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("localeId", data.getString("localeId"));
		return map;
	}

}
