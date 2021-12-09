package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.json.Jsonizable;

public abstract class History implements Jsonizable {

	private final int time;

	protected History(int time) {
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
