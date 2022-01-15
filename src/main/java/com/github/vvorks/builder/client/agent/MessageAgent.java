/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.common.json.Json;

/**
 * メッセージエージェント
 */
public class MessageAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static MessageAgent INSTANCE = new MessageAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static final MessageAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private MessageAgent() {
	}

	@Override
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setInt(name + "OwnerProjectId", from.getInt("ownerProjectId"));
		rec.setString(name + "MessageId", from.getString("messageId"));
		rec.setString(name + "LocaleId", from.getString("localeId"));
	}

	@Override
	public void setValue(DataRecord rec, String name, DataRecord candidate) {
		rec.setInt(name + "OwnerProjectId", candidate.getInt("ownerProjectId"));
		rec.setString(name + "MessageId", candidate.getString("messageId"));
		rec.setString(name + "LocaleId", candidate.getString("localeId"));
	}

}
