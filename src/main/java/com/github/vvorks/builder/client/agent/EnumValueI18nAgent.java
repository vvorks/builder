/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import java.util.Map;
import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.shared.common.json.Json;

/**
 * 列挙値(I18n)エージェント
 */
public class EnumValueI18nAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static final EnumValueI18nAgent INSTANCE = new EnumValueI18nAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static EnumValueI18nAgent get() {
		return INSTANCE;
	}

	/** プライベートコンストラクタ */
	private EnumValueI18nAgent() {
	}

	@Override
	public String getTypeName() {
		return "EnumValueI18n";
	}

	@Override
	public void setValue(DataRecord rec, String name, Json from) {
		rec.setInt(name + "OwnerOwnerEnumId", from.getInt("ownerOwnerEnumId"));
		rec.setString(name + "OwnerValueId", from.getString("ownerValueId"));
		rec.setString(name + "TargetLocaleId", from.getString("targetLocaleId"));
	}

	@Override
	public void setValue(DataRecord rec, String name, DataRecord candidate) {
		rec.setInt(name + "OwnerOwnerEnumId", candidate.getInt("ownerOwnerEnumId"));
		rec.setString(name + "OwnerValueId", candidate.getString("ownerValueId"));
		rec.setString(name + "TargetLocaleId", candidate.getString("targetLocaleId"));
	}

	@Override
	public Json getContentCriteria(Map<String, String> param) {
		Json criteria = Json.createObject();
		criteria.setString("ownerOwnerEnumId", param.get("ownerOwnerEnumId"));
		criteria.setString("ownerValueId", param.get("ownerValueId"));
		criteria.setString("targetLocaleId", param.get("targetLocaleId"));
		return criteria;
	}

}
