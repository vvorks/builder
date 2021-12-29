package com.github.vvorks.builder.client.agent;

import com.github.vvorks.builder.client.common.ui.DataRecord;
import com.github.vvorks.builder.client.common.ui.DataRecordAgent;
import com.github.vvorks.builder.common.json.Json;

public class DataTypeAgent extends DataRecordAgent {

	public static DataTypeAgent INSTANCE = new DataTypeAgent();

	public static final DataTypeAgent get() {
		return INSTANCE;
	}

	private DataTypeAgent() {
	}

	@Override
	public void setValue(DataRecord rec, String name, Json from) {
		// TODO Candidateから得られたfromのJSONから、値及びタイトルをコピー
	}

}
