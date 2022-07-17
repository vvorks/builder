/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.common.json.Json;

/**
 * フォームバリエーションエージェント
 */
public class FormVariantAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final FormVariantAgent INSTANCE = new FormVariantAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static final FormVariantAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private FormVariantAgent() {
	}

	@Override
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setInt(name + "VariantId", from.getInt("variantId"));
	}

	@Override
	public void setValue(DataRecord rec, String name, DataRecord candidate) {
		rec.setInt(name + "VariantId", candidate.getInt("variantId"));
	}

}
