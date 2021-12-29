package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.common.json.Json;

public abstract class DataRecordAgent {

	public String getTitle(DataRecord rec, String name) {
		return rec.getString(name + "_title");
	}

	public abstract void setValue(DataRecord rec, String name, Json from);

}
