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
 * 列挙値エージェント
 */
public class EnumValueAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final EnumValueAgent INSTANCE = new EnumValueAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static EnumValueAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private EnumValueAgent() {
	}

	@Override
	public String getTypeName() {
		return "EnumValue";
	}

	@Override
	public Json getValue(DataRecord rec, String name) {
		Json data = Json.createObject();
		data.setInt("ownerEnumId", rec.getInt(name + "OwnerEnumId"));
		data.setString("valueId", rec.getString(name + "ValueId"));
		return data;
	}

	@Override
	public void setValue(DataRecord rec, String name, Json data) {
		rec.setInt(name + "OwnerEnumId", data.getInt("ownerEnumId"));
		rec.setString(name + "ValueId", data.getString("valueId"));
	}

	@Override
	public Json fromParam(Map<String, String> param) {
		Json key = Json.createObject();
		key.setInt("ownerEnumId", asInt(param.get("ownerEnumId")));
		key.setString("valueId", param.get("valueId"));
		return key;
	}

	@Override
	public Map<String, String> toParam(Json data) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("ownerEnumId", toString(data.getInt("ownerEnumId")));
		map.put("valueId", data.getString("valueId"));
		return map;
	}

}
