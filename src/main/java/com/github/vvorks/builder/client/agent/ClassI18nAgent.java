/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.common.json.Json;

/**
 * クラス(I18n)エージェント
 */
public class ClassI18nAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final ClassI18nAgent INSTANCE = new ClassI18nAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static final ClassI18nAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private ClassI18nAgent() {
	}

	@Override
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setInt(name + "OwnerClassId", from.getInt("ownerClassId"));
		rec.setString(name + "TargetLocaleId", from.getString("targetLocaleId"));
	}

	@Override
	public void setValue(DataRecord rec, String name, DataRecord candidate) {
		rec.setInt(name + "OwnerClassId", candidate.getInt("ownerClassId"));
		rec.setString(name + "TargetLocaleId", candidate.getString("targetLocaleId"));
	}

}
