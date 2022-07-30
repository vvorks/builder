/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.common.json.Json;

/**
 * Builderエージェント
 */
public class BuilderAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final BuilderAgent INSTANCE = new BuilderAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static BuilderAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private BuilderAgent() {
	}

	@Override
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setString(name + "ClassName", from.getString("className"));
	}

	@Override
	public void setValue(DataRecord rec, String name, DataRecord candidate) {
		rec.setString(name + "ClassName", candidate.getString("className"));
	}

}
