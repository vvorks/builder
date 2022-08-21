/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import java.util.Map;
import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.shared.common.json.Json;

/**
 * プロジェクトエージェント
 */
public class ProjectAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final ProjectAgent INSTANCE = new ProjectAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static ProjectAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private ProjectAgent() {
	}

	@Override
	public String getTypeName() {
		return "Project";
	}

	@Override
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setInt(name + "ProjectId", from.getInt("projectId"));
	}

	@Override
	public void setValue(DataRecord rec, String name, DataRecord candidate) {
		rec.setInt(name + "ProjectId", candidate.getInt("projectId"));
	}

	@Override
	public Json getContentCriteria(Map<String, String> param) {
		Json criteria = Json.createObject();
		criteria.setString("projectId", param.get("projectId"));
		return criteria;
	}

}
