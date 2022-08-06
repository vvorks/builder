/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.shared.common.json.Json;

/**
 * クエリーエージェント
 */
public class QueryAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final QueryAgent INSTANCE = new QueryAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static QueryAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private QueryAgent() {
	}

	@Override
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setInt(name + "QueryId", from.getInt("queryId"));
	}

	@Override
	public void setValue(DataRecord rec, String name, DataRecord candidate) {
		rec.setInt(name + "QueryId", candidate.getInt("queryId"));
	}

}
