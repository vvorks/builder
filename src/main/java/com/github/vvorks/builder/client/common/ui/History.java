package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.json.Jsonizable;
import com.github.vvorks.builder.common.logging.Logger;

public abstract class History implements Jsonizable {

	public static final Class<?> THIS = History.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

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
		json.setNumber("time", time);
		return json;
	}

}
