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
	public static final MessageAgent INSTANCE = new MessageAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static MessageAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private MessageAgent() {
	}

	@Override
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setInt(name + "MessageId", from.getInt("messageId"));
	}

	@Override
	public void setValue(DataRecord rec, String name, DataRecord candidate) {
		rec.setInt(name + "MessageId", candidate.getInt("messageId"));
	}

}
