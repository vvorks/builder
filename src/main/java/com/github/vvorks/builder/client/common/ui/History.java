package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.json.Jsonizable;
import com.github.vvorks.builder.common.logging.Logger;

public abstract class History implements Jsonizable {

	public static final Logger LOGGER = Logger.createLogger(History.class);

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
