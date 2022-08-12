package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.shared.common.lang.Asserts;

public class UiTab extends UiButton implements Selectable.Listener {

	private UiNode related;

	public UiTab(String name) {
		super(name);
		setEnable(false);
	}

	public UiTab(UiTab src) {
		super(src);
	}

	@Override
	public UiTab copy() {
		return new UiTab(this);
	}

	@Override
	public void setRelated(UiNode related) {
		Asserts.require(related == null || related.getParent() instanceof UiDeckGroup);
		if (this.related != null) {
			UiDeckGroup deck = (UiDeckGroup) this.related.getParent();
			deck.removeSelectableListener(this);
		}
		this.related = related;
		if (this.related != null) {
			UiDeckGroup deck = (UiDeckGroup) this.related.getParent();
			deck.addSelectableListener(this);
		}
	}

	@Override
	public int onMouseClick(UiNode target, int x, int y, int mods, int time) {
		if (related == null) {
			return EVENT_IGNORED;
		}
		UiDeckGroup deck = (UiDeckGroup) related.getParent();
		return deck.select(deck.indexOf(related)) ? EVENT_AFFECTED : EVENT_IGNORED;
	}

	@Override
	public void onSelected(int selected) {
		if (related == null) {
			return;
		}
		UiDeckGroup deck = (UiDeckGroup) related.getParent();
		if (deck.indexOf(related) == selected) {
			this.setEnable(true);
		}
	}

	@Override
	public void onDeselected(int deselected) {
		if (related == null) {
			return;
		}
		UiDeckGroup deck = (UiDeckGroup) related.getParent();
		if (deck.indexOf(related) == deselected) {
			this.setEnable(false);
		}
	}

}
