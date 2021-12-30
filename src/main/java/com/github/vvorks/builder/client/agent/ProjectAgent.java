/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.common.json.Json;

/**
 * プロジェクトエージェント
 */
public class ProjectAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static ProjectAgent INSTANCE = new ProjectAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static final ProjectAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private ProjectAgent() {
	}

	@Override
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setInt(name + "ProjectId", from.getInt("projectId"));
	}

}
