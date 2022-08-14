/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import java.util.Map;
import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.shared.common.json.Json;

/**
 * メッセージ(I18n)エージェント
 */
public class MessageI18nAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final MessageI18nAgent INSTANCE = new MessageI18nAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static MessageI18nAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private MessageI18nAgent() {
	}

	@Override
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setInt(name + "OwnerMessageId", from.getInt("ownerMessageId"));
		rec.setString(name + "TargetLocaleId", from.getString("targetLocaleId"));
	}

	@Override
	public void setValue(DataRecord rec, String name, DataRecord candidate) {
		rec.setInt(name + "OwnerMessageId", candidate.getInt("ownerMessageId"));
		rec.setString(name + "TargetLocaleId", candidate.getString("targetLocaleId"));
	}

	@Override
	public Json getContentCriteria(Map<String, String> param) {
		Json criteria = Json.createObject();
		criteria.setString("ownerMessageId", param.get("ownerMessageId"));
		criteria.setString("targetLocaleId", param.get("targetLocaleId"));
		return criteria;
	}

}
