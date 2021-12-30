/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.common.json.Json;

/**
 * フィールドエージェント
 */
public class FieldAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static FieldAgent INSTANCE = new FieldAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static final FieldAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private FieldAgent() {
	}

	@Override
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setInt(name + "FieldId", from.getInt("fieldId"));
	}

}
