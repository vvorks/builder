/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.common.json.Json;

/**
 * フォームエージェント
 */
public class FormAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final FormAgent INSTANCE = new FormAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static final FormAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private FormAgent() {
	}

	@Override
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setInt(name + "FormId", from.getInt("formId"));
	}

	@Override
	public void setValue(DataRecord rec, String name, DataRecord candidate) {
		rec.setInt(name + "FormId", candidate.getInt("formId"));
	}

}
