/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.common.json.Json;

/**
 * ローカライズドリソースエージェント
 */
public class LocalizedResourceAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final LocalizedResourceAgent INSTANCE = new LocalizedResourceAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static final LocalizedResourceAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private LocalizedResourceAgent() {
	}

	@Override
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setInt(name + "OwnerResourceId", from.getInt("ownerResourceId"));
		rec.setString(name + "Locale", from.getString("locale"));
	}

	@Override
	public void setValue(DataRecord rec, String name, DataRecord candidate) {
		rec.setInt(name + "OwnerResourceId", candidate.getInt("ownerResourceId"));
		rec.setString(name + "Locale", candidate.getString("locale"));
	}

}
