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
 * 列挙(I18n)エージェント
 */
public class EnumI18nAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final EnumI18nAgent INSTANCE = new EnumI18nAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static EnumI18nAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private EnumI18nAgent() {
	}

	@Override
	public String getTypeName() {
		return "EnumI18n";
	}

	@Override
	public Json getValue(DataRecord rec, String name) {
		Json data = Json.createObject();
		data.setInt("ownerEnumId", rec.getInt(name + "OwnerEnumId"));
		data.setString("targetLocaleId", rec.getString(name + "TargetLocaleId"));
		return data;
	}

	@Override
	public void setValue(DataRecord rec, String name, Json data) {
		rec.setInt(name + "OwnerEnumId", data.getInt("ownerEnumId"));
		rec.setString(name + "TargetLocaleId", data.getString("targetLocaleId"));
	}

	@Override
	public Json fromParam(Map<String, String> param) {
		Json key = Json.createObject();
		key.setInt("ownerEnumId", asInt(param.get("ownerEnumId")));
		key.setString("targetLocaleId", param.get("targetLocaleId"));
		return key;
	}

	@Override
	public Map<String, String> toParam(Json data) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("ownerEnumId", toString(data.getInt("ownerEnumId")));
		map.put("targetLocaleId", data.getString("targetLocaleId"));
		return map;
	}

}
