package com.github.vvorks.builder.client.common.ui;

public class UiTab extends UiButton implements Selectable.Listener {

	private UiDeckGroup deck;

	private int index;

	public UiTab(String name, UiDeckGroup deck, int index) {
		super(name);
		this.deck = deck;
		this.index = index;
		setEnable(false);
		deck.addSelectableListener(this);
	}

	public UiTab(UiTab src) {
		super(src);
		this.deck = src.deck;
		this.index = src.index;
		deck.addSelectableListener(this);
	}

	@Override
	public UiTab copy() {
		return new UiTab(this);
	}

	@Override
	public int onMouseClick(UiNode target, int x, int y, int mods, int time) {
		return deck.select(index) ? EVENT_AFFECTED : EVENT_IGNORED;
	}

	@Override
	public void onSelected(int selected) {
		if (index == selected) {
			this.setEnable(true);
		}
	}

	@Override
	public void onDeselected(int deselected) {
		if (index == deselected) {
			this.setEnable(false);
		}
	}

}
