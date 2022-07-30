/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.common.json.Json;

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
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setInt(name + "ClassId", from.getInt("classId"));
	}

	@Override
	public void setValue(DataRecord rec, String name, DataRecord candidate) {
		rec.setInt(name + "ClassId", candidate.getInt("classId"));
	}

}
