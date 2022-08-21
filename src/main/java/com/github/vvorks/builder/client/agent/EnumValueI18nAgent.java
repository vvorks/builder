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
 * 列挙値(I18n)エージェント
 */
public class EnumValueI18nAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final EnumValueI18nAgent INSTANCE = new EnumValueI18nAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static EnumValueI18nAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private EnumValueI18nAgent() {
	}

	@Override
	public String getTypeName() {
		return "EnumValueI18n";
	}

	@Override
	public Json getValue(DataRecord rec, String name) {
		Json data = Json.createObject();
		data.setInt("ownerOwnerEnumId", rec.getInt(name + "OwnerOwnerEnumId"));
		data.setString("ownerValueId", rec.getString(name + "OwnerValueId"));
		data.setString("targetLocaleId", rec.getString(name + "TargetLocaleId"));
		return data;
	}

	@Override
	public void setValue(DataRecord rec, String name, Json data) {
		rec.setInt(name + "OwnerOwnerEnumId", data.getInt("ownerOwnerEnumId"));
		rec.setString(name + "OwnerValueId", data.getString("ownerValueId"));
		rec.setString(name + "TargetLocaleId", data.getString("targetLocaleId"));
	}

	@Override
	public Json fromParam(Map<String, String> param) {
		Json key = Json.createObject();
		key.setInt("ownerOwnerEnumId", asInt(param.get("ownerOwnerEnumId")));
		key.setString("ownerValueId", param.get("ownerValueId"));
		key.setString("targetLocaleId", param.get("targetLocaleId"));
		return key;
	}

	@Override
	public Map<String, String> toParam(Json data) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("ownerOwnerEnumId", toString(data.getInt("ownerOwnerEnumId")));
		map.put("ownerValueId", data.getString("ownerValueId"));
		map.put("targetLocaleId", data.getString("targetLocaleId"));
		return map;
	}

}
