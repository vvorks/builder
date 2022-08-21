/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import java.util.Map;
import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.shared.common.json.Json;

/**
 * スタイルエージェント
 */
public class StyleAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final StyleAgent INSTANCE = new StyleAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static StyleAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private StyleAgent() {
	}

	@Override
	public String getTypeName() {
		return "Style";
	}

	@Override
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setInt(name + "StyleId", from.getInt("styleId"));
	}

	@Override
	public void setValue(DataRecord rec, String name, DataRecord candidate) {
		rec.setInt(name + "StyleId", candidate.getInt("styleId"));
	}

	@Override
	public Json getContentCriteria(Map<String, String> param) {
		Json criteria = Json.createObject();
		criteria.setString("styleId", param.get("styleId"));
		return criteria;
	}

}
