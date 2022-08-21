/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import java.util.Map;
import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.shared.common.json.Json;

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
	public static PageAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private PageAgent() {
	}

	@Override
	public String getTypeName() {
		return "Page";
	}

	@Override
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setInt(name + "PageId", from.getInt("pageId"));
	}

	@Override
	public void setValue(DataRecord rec, String name, DataRecord candidate) {
		rec.setInt(name + "PageId", candidate.getInt("pageId"));
	}

	@Override
	public Json getContentCriteria(Map<String, String> param) {
		Json criteria = Json.createObject();
		criteria.setString("pageId", param.get("pageId"));
		return criteria;
	}

}
