/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.common.json.Json;

/**
 * レイアウトエージェント
 */
public class LayoutAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final LayoutAgent INSTANCE = new LayoutAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static LayoutAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private LayoutAgent() {
	}

	@Override
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setInt(name + "LayoutId", from.getInt("layoutId"));
	}

	@Override
	public void setValue(DataRecord rec, String name, DataRecord candidate) {
		rec.setInt(name + "LayoutId", candidate.getInt("layoutId"));
	}

}
