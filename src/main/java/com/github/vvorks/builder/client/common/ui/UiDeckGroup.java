package com.github.vvorks.builder.client.common.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.vvorks.builder.shared.common.lang.Asserts;

public class UiDeckGroup extends UiGroup implements Selectable {

	private int selected = -1;

	private List<Selectable.Listener> listeners;

	public UiDeckGroup(String name) {
		super(name);
		listeners = null;
	}

	public UiDeckGroup(UiDeckGroup src) {
		super(src);
		this.selected = src.selected;
		if (src.listeners != null) {
			listeners = new ArrayList<>(src.listeners);
		}
	}

	@Override
	public UiDeckGroup copy() {
		return new UiDeckGroup(this);
	}

	@Override
	public void onMount() {
		super.onMount();
		if (selected == -1) {
			select(0);
		}
	}

	public boolean select(int index) {
		Asserts.require(0 <= index && index < size());
		if (index == selected) {
			return false;
		}
		UiNode target = getChild(index);
		getChildren().forEach(node -> node.setVisible(false));
		target.setVisible(true);
		setSelected(index);
		return true;
	}

	public boolean deselect(int index) {
		Asserts.require(0 <= index && index < size());
		if (index != selected) {
			return false;
		}
		UiNode target = getChild(index);
		target.setVisible(false);
		setSelected(-1);
		return true;
	}

	private void setSelected(int index) {
		if (selected != -1) {
			notifyDeselected(selected);
		}
		selected = index;
		if (selected != -1) {
			notifySelected(selected);
		}
	}

	private void notifySelected(int index) {
		for (Selectable.Listener l : getSelectableListeners()) {
			l.onSelected(index);
		}
	}

	private void notifyDeselected(int index) {
		for (Selectable.Listener l : getSelectableListeners()) {
			l.onDeselected(index);
		}
	}

	@Override
	public void addSelectableListener(Selectable.Listener listener) {
		if (listeners == null) {
			listeners = new ArrayList<>();
		}
		listeners.add(listener);
	}

	@Override
	public void removeSelectableListener(Selectable.Listener listener) {
		if (listeners == null) {
			return;
		}
		listeners.remove(listener);
	}

	private Iterable<Selectable.Listener> getSelectableListeners() {
		if (listeners == null) {
			return Collections.emptyList();
		}
		return listeners;
	}

	@Override
	public boolean isSelected(int index) {
		return index == selected;
	}

}
