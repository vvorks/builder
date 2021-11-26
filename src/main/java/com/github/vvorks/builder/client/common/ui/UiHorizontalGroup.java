package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.common.logging.Logger;

public class UiHorizontalGroup extends UiGroup {

	public static final Class<?> THIS = UiHorizontalGroup.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	public UiHorizontalGroup(String name) {
		super(name);
	}

	protected UiHorizontalGroup(UiGroup src) {
		super(src);
	}

	@Override
	public UiHorizontalGroup copy() {
		return new UiHorizontalGroup(this);
	}

	@Override
	public void onMount() {
		relocateChildren();
		super.onMount();
	}

	private void relocateChildren() {
		int spc = getSpacingWidthPx();
		int total = spc;
		for (UiNode child : getChildrenIf(c -> !c.isDeleted() && c.isVisible())) {
			Length left = new Length(total);
			Length width = child.getWidth();
			Length top = child.getTop();
			Length bottom = child.getBottom();
			Length height = child.getHeight();
			if (top == null && bottom == null && height == null) {
				top = bottom = getSpacingHeight();
			}
			child.setBounds(left, top, null, bottom, width, height);
			total += child.getWidthPx() + spc;
		}
		setScrollWidth(total);
	}

}
