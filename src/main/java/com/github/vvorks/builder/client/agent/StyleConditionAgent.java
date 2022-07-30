/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.common.json.Json;

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
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setString(name, from.getString("value"));
	}

	@Override
	public void setValue(DataRecord rec, String name, DataRecord candidate) {
		rec.setString(name, candidate.getString("value"));
	}

}
