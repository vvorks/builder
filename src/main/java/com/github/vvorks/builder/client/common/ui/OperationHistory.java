package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.shared.common.json.Json;
import com.github.vvorks.builder.shared.common.json.Jsonizable;

public abstract class OperationHistory implements Jsonizable {

	private final int time;

	protected OperationHistory(int time) {
		this.time = time;
	}

	public int getTime() {
		return time;
	}

	public abstract void undo();

	public abstract void redo();

	@Override
	public Json toJson() {
		Json json = Json.createObject();
		json.setInt("time", time);
		return json;
	}

}
