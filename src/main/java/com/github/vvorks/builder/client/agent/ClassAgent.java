/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import java.util.Map;
import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.shared.common.json.Json;

/**
 * クラスエージェント
 */
public class ClassAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final ClassAgent INSTANCE = new ClassAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static ClassAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private ClassAgent() {
	}

	@Override
	public String getTypeName() {
		return "Class";
	}

	@Override
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setInt(name + "ClassId", from.getInt("classId"));
	}

	@Override
	public void setValue(DataRecord rec, String name, DataRecord candidate) {
		rec.setInt(name + "ClassId", candidate.getInt("classId"));
	}

	@Override
	public Json getContentCriteria(Map<String, String> param) {
		Json criteria = Json.createObject();
		criteria.setString("classId", param.get("classId"));
		return criteria;
	}

}
