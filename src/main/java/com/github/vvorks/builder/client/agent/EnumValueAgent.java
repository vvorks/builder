/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import java.util.Map;
import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.shared.common.json.Json;

/**
 * 列挙値エージェント
 */
public class EnumValueAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final EnumValueAgent INSTANCE = new EnumValueAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static EnumValueAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private EnumValueAgent() {
	}

	@Override
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setInt(name + "OwnerEnumId", from.getInt("ownerEnumId"));
		rec.setString(name + "ValueId", from.getString("valueId"));
	}

	@Override
	public void setValue(DataRecord rec, String name, DataRecord candidate) {
		rec.setInt(name + "OwnerEnumId", candidate.getInt("ownerEnumId"));
		rec.setString(name + "ValueId", candidate.getString("valueId"));
	}

	@Override
	public Json getContentCriteria(Map<String, String> param) {
		Json criteria = Json.createObject();
		criteria.setString("ownerEnumId", param.get("ownerEnumId"));
		criteria.setString("valueId", param.get("valueId"));
		return criteria;
	}

}
