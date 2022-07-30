/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.common.json.Json;

/**
 * フィールド(I18n)エージェント
 */
public class FieldI18nAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final FieldI18nAgent INSTANCE = new FieldI18nAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static FieldI18nAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private FieldI18nAgent() {
	}

	@Override
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setInt(name + "OwnerFieldId", from.getInt("ownerFieldId"));
		rec.setString(name + "TargetLocaleId", from.getString("targetLocaleId"));
	}

	@Override
	public void setValue(DataRecord rec, String name, DataRecord candidate) {
		rec.setInt(name + "OwnerFieldId", candidate.getInt("ownerFieldId"));
		rec.setString(name + "TargetLocaleId", candidate.getString("targetLocaleId"));
	}

}
