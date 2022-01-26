/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.common.json.Json;

/**
 * リソースエージェント
 */
public class ResourceAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static ResourceAgent INSTANCE = new ResourceAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static final ResourceAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private ResourceAgent() {
	}

	@Override
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setInt(name + "ResourceId", from.getInt("resourceId"));
	}

	@Override
	public void setValue(DataRecord rec, String name, DataRecord candidate) {
		rec.setInt(name + "ResourceId", candidate.getInt("resourceId"));
	}

}
