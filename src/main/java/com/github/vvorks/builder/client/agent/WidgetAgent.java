/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.common.json.Json;

/**
 * ウィジェットエージェント
 */
public class WidgetAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final WidgetAgent INSTANCE = new WidgetAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static final WidgetAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private WidgetAgent() {
	}

	@Override
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setInt(name + "WidgetId", from.getInt("widgetId"));
	}

	@Override
	public void setValue(DataRecord rec, String name, DataRecord candidate) {
		rec.setInt(name + "WidgetId", candidate.getInt("widgetId"));
	}

}
