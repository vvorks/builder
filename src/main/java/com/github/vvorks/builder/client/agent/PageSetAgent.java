/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.common.json.Json;

/**
 * ページセットエージェント
 */
public class PageSetAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final PageSetAgent INSTANCE = new PageSetAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static PageSetAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private PageSetAgent() {
	}

	@Override
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setInt(name + "PageSetId", from.getInt("pageSetId"));
	}

	@Override
	public void setValue(DataRecord rec, String name, DataRecord candidate) {
		rec.setInt(name + "PageSetId", candidate.getInt("pageSetId"));
	}

}
