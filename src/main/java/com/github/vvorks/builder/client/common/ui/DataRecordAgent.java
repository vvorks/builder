package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.shared.common.json.Json;

public abstract class DataRecordAgent {

	public String getTitle(DataRecord rec, String name) {
		return rec.getString(name + "_title");
	}

	public void setTitle(DataRecord rec, String name, Json from) {
		rec.setString(name + "_title", from.getString("_title"));
	}

	public void setTitle(DataRecord rec, String name, DataRecord candidate) {
		rec.setString(name + "_title", candidate.getString("_title"));
	}

	public abstract void setValue(DataRecord rec, String name, Json from);

	public abstract void setValue(DataRecord rec, String name, DataRecord candidate);

}
