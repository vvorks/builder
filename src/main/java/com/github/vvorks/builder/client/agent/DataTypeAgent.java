/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import java.util.Map;

import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.shared.common.json.Json;

/**
 * データ型エージェント
 */
public class DataTypeAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final DataTypeAgent INSTANCE = new DataTypeAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static DataTypeAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private DataTypeAgent() {
	}

	@Override
	public String getTypeName() {
		return "DataType";
	}

	@Override
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setString(name, from.getString("value"));
	}

	@Override
	public void setValue(DataRecord rec, String name, DataRecord candidate) {
		rec.setString(name, candidate.getString("value"));
	}

	@Override
	public Json getContentCriteria(Map<String, String> param) {
		return null;
	}

}
