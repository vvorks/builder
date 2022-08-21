/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import java.util.Map;
import java.util.LinkedHashMap;
import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.shared.common.json.Json;

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
	public String getTypeName() {
		return "Message";
	}

	@Override
	public Json getValue(DataRecord rec, String name) {
		Json data = Json.createObject();
		data.setInt("messageId", rec.getInt(name + "MessageId"));
		return data;
	}

	@Override
	public void setValue(DataRecord rec, String name, Json data) {
		rec.setInt(name + "MessageId", data.getInt("messageId"));
	}

	@Override
	public Json fromParam(Map<String, String> param) {
		Json key = Json.createObject();
		key.setInt("messageId", asInt(param.get("messageId")));
		return key;
	}

	@Override
	public Map<String, String> toParam(Json data) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("messageId", toString(data.getInt("messageId")));
		return map;
	}

}
