/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import java.util.Map;
import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.shared.common.json.Json;

/**
 * スタイル条件エージェント
 */
public class StyleConditionAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final StyleConditionAgent INSTANCE = new StyleConditionAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static StyleConditionAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private StyleConditionAgent() {
	}

	@Override
	public String getTypeName() {
		return "StyleCondition";
	}

	@Override
	public Json getValue(DataRecord rec, String name) {
		Json data = Json.createObject();
		data.setString(name, rec.getString("value"));
		return data;
	}

	@Override
	public void setValue(DataRecord rec, String name, Json data) {
		rec.setString(name, data.getString("value"));
	}

	@Override
	public Json fromParam(Map<String, String> param) {
		return null;
	}

	@Override
	public Map<String, String> toParam(Json data) {
		return null;
	}

}
