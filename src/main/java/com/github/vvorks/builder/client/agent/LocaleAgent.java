/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import java.util.Map;
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
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setString(name + "LocaleId", from.getString("localeId"));
	}

	@Override
	public void setValue(DataRecord rec, String name, DataRecord candidate) {
		rec.setString(name + "LocaleId", candidate.getString("localeId"));
	}

	@Override
	public Json getContentCriteria(Map<String, String> param) {
		Json criteria = Json.createObject();
		criteria.setString("localeId", param.get("localeId"));
		return criteria;
	}

}
