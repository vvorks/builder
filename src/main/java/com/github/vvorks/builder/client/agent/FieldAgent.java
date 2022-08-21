/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import java.util.Map;
import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.shared.common.json.Json;

/**
 * フィールドエージェント
 */
public class FieldAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final FieldAgent INSTANCE = new FieldAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static FieldAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private FieldAgent() {
	}

	@Override
	public String getTypeName() {
		return "Field";
	}

	@Override
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setInt(name + "FieldId", from.getInt("fieldId"));
	}

	@Override
	public void setValue(DataRecord rec, String name, DataRecord candidate) {
		rec.setInt(name + "FieldId", candidate.getInt("fieldId"));
	}

	@Override
	public Json getContentCriteria(Map<String, String> param) {
		Json criteria = Json.createObject();
		criteria.setString("fieldId", param.get("fieldId"));
		return criteria;
	}

}
