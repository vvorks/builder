/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.common.json.Json;

/**
 * ページエージェント
 */
public class PageAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final PageAgent INSTANCE = new PageAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static final PageAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private PageAgent() {
	}

	@Override
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setInt(name + "PageId", from.getInt("pageId"));
	}

	@Override
	public void setValue(DataRecord rec, String name, DataRecord candidate) {
		rec.setInt(name + "PageId", candidate.getInt("pageId"));
	}

}
