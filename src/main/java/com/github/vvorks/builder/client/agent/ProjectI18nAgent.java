/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import java.util.Map;
import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.shared.common.json.Json;

/**
 * プロジェクト(I18n)エージェント
 */
public class ProjectI18nAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final ProjectI18nAgent INSTANCE = new ProjectI18nAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static ProjectI18nAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private ProjectI18nAgent() {
	}

	@Override
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setInt(name + "OwnerProjectId", from.getInt("ownerProjectId"));
		rec.setString(name + "TargetLocaleId", from.getString("targetLocaleId"));
	}

	@Override
	public void setValue(DataRecord rec, String name, DataRecord candidate) {
		rec.setInt(name + "OwnerProjectId", candidate.getInt("ownerProjectId"));
		rec.setString(name + "TargetLocaleId", candidate.getString("targetLocaleId"));
	}

	@Override
	public Json getContentCriteria(Map<String, String> param) {
		Json criteria = Json.createObject();
		criteria.setString("ownerProjectId", param.get("ownerProjectId"));
		criteria.setString("targetLocaleId", param.get("targetLocaleId"));
		return criteria;
	}

}
