/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.agent;

import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.common.json.Json;

/**
 * 列挙値エージェント
 */
public class EnumValueAgent extends DataRecordAgent {

	/** このクラスのシングルトンインスタンス */
	public static EnumValueAgent INSTANCE = new EnumValueAgent();

	/**
	 * このクラスのシングルトンインスタンスを取得する
	 *
	 * @return このクラスのシングルトンインスタンス
	 */
	public static final EnumValueAgent get() {
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

}
