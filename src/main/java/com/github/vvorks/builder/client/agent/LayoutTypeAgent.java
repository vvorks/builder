/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.common.json.Json;

/**
 * レイアウト種別エージェント
 */
public class LayoutTypeAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static LayoutTypeAgent INSTANCE = new LayoutTypeAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static final LayoutTypeAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private LayoutTypeAgent() {
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
