package com.github.vvorks.builder.client.common.ui;

import java.util.ArrayList;
import java.util.List;

import com.github.vvorks.builder.common.json.Json;
import com.github.vvorks.builder.common.json.Jsonizable;
import com.github.vvorks.builder.common.logging.Logger;

public class HistoryBuffer implements Jsonizable {

	public static final Class<?> THIS = HistoryBuffer.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	private final List<History> histories;

	private int position;

	public HistoryBuffer() {
		histories = new ArrayList<>();
		position = histories.size();
	}

	public void done(History h) {
		if (position < histories.size()) {
			histories.subList(position, histories.size()).clear();
		}
		histories.add(h);
		position = histories.size();
	}

	public boolean canUndo() {
		return position > 0;
	}

	public void undo() {
		if (!canUndo()) {
			return;
		}
		int i = position - 1;
		int t = histories.get(i).getTime();
		while (i >= 0 && t == histories.get(i).getTime()) {
			histories.get(i--).undo();
		}
		position = i + 1;
	}

	public boolean canRedo() {
		return position < histories.size();
	}

	public void redo() {
		if (!canRedo()) {
			return;
		}
		int n = histories.size();
		int i = position;
		int t = histories.get(i).getTime();
		while (i < n && t == histories.get(i).getTime()) {
			histories.get(i++).redo();
		}
		position = i;
	}

	@Override
	public Json toJson() {
		Json json = Json.createObject();
		json.setNumber("position", position);
		Json hs = json.setNewArray("histories");
		for (History h : histories) {
			hs.add(h.toJson());
		}
		return json;
	}

}
