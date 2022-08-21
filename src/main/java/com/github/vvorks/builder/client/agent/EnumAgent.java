/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import java.util.Map;
import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.shared.common.json.Json;

/**
 * 列挙エージェント
 */
public class EnumAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final EnumAgent INSTANCE = new EnumAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static EnumAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private EnumAgent() {
	}

	@Override
	public String getTypeName() {
		return "Enum";
	}

	@Override
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setInt(name + "EnumId", from.getInt("enumId"));
	}

	@Override
	public void setValue(DataRecord rec, String name, DataRecord candidate) {
		rec.setInt(name + "EnumId", candidate.getInt("enumId"));
	}

	@Override
	public Json getContentCriteria(Map<String, String> param) {
		Json criteria = Json.createObject();
		criteria.setString("enumId", param.get("enumId"));
		return criteria;
	}

}
