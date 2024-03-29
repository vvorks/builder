package com.github.vvorks.builder.client.common.ui;

import java.util.ArrayList;
import java.util.List;

import com.github.vvorks.builder.shared.common.json.Json;
import com.github.vvorks.builder.shared.common.json.Jsonizable;

public class HistoryBuffer implements Jsonizable {

	private final List<OperationHistory> histories;

	private int position;

	public HistoryBuffer() {
		histories = new ArrayList<>();
		position = histories.size();
	}

	public void done(OperationHistory h) {
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
		json.setInt("position", position);
		Json hs = json.setNewArray("histories");
		for (OperationHistory h : histories) {
			hs.add(h.toJson());
		}
		return json;
	}

}
