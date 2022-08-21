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
 * 列挙エージェント
 */
public class EnumAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final EnumAgent INSTANCE = new EnumAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static EnumAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private EnumAgent() {
	}

	@Override
	public String getTypeName() {
		return "Enum";
	}

	@Override
	public Json getValue(DataRecord rec, String name) {
		Json data = Json.createObject();
		data.setInt("enumId", rec.getInt(name + "EnumId"));
		return data;
	}

	@Override
	public void setValue(DataRecord rec, String name, Json data) {
		rec.setInt(name + "EnumId", data.getInt("enumId"));
	}

	@Override
	public Json fromParam(Map<String, String> param) {
		Json key = Json.createObject();
		key.setInt("enumId", asInt(param.get("enumId")));
		return key;
	}

	@Override
	public Map<String, String> toParam(Json data) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("enumId", toString(data.getInt("enumId")));
		return map;
	}

}
