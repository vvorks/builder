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
 * メッセージ(I18n)エージェント
 */
public class MessageI18nAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final MessageI18nAgent INSTANCE = new MessageI18nAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static MessageI18nAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private MessageI18nAgent() {
	}

	@Override
	public String getTypeName() {
		return "MessageI18n";
	}

	@Override
	public Json getValue(DataRecord rec, String name) {
		Json data = Json.createObject();
		data.setInt("ownerMessageId", rec.getInt(name + "OwnerMessageId"));
		data.setString("targetLocaleId", rec.getString(name + "TargetLocaleId"));
		return data;
	}

	@Override
	public void setValue(DataRecord rec, String name, Json data) {
		rec.setInt(name + "OwnerMessageId", data.getInt("ownerMessageId"));
		rec.setString(name + "TargetLocaleId", data.getString("targetLocaleId"));
	}

	@Override
	public Json fromParam(Map<String, String> param) {
		Json key = Json.createObject();
		key.setInt("ownerMessageId", asInt(param.get("ownerMessageId")));
		key.setString("targetLocaleId", param.get("targetLocaleId"));
		return key;
	}

	@Override
	public Map<String, String> toParam(Json data) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("ownerMessageId", toString(data.getInt("ownerMessageId")));
		map.put("targetLocaleId", data.getString("targetLocaleId"));
		return map;
	}

}
