/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import java.util.Map;
import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.shared.common.json.Json;

/**
 * 列挙(I18n)エージェント
 */
public class EnumI18nAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final EnumI18nAgent INSTANCE = new EnumI18nAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static EnumI18nAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private EnumI18nAgent() {
	}

	@Override
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setInt(name + "OwnerEnumId", from.getInt("ownerEnumId"));
		rec.setString(name + "TargetLocaleId", from.getString("targetLocaleId"));
	}

	@Override
	public void setValue(DataRecord rec, String name, DataRecord candidate) {
		rec.setInt(name + "OwnerEnumId", candidate.getInt("ownerEnumId"));
		rec.setString(name + "TargetLocaleId", candidate.getString("targetLocaleId"));
	}

	@Override
	public Json getContentCriteria(Map<String, String> param) {
		Json criteria = Json.createObject();
		criteria.setString("ownerEnumId", param.get("ownerEnumId"));
		criteria.setString("targetLocaleId", param.get("targetLocaleId"));
		return criteria;
	}

}
