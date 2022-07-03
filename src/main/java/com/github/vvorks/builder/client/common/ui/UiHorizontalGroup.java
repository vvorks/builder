package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.client.ClientSettings;

public class UiHorizontalGroup extends UiGroup {

	public UiHorizontalGroup(String name) {
		super(name);
	}

	public UiHorizontalGroup(String name, Scrollable scrollable) {
		super(name, scrollable);
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

	@Override
	public int onMouseWheel(UiNode target, int x, int y, int dx, int dy, int mods, int time) {
		int result = EVENT_CONSUMED;
		int delta = dy * ClientSettings.WHEEL_SCALE;
		int oldLeft = getScrollLeftPx();
		int maxLeft = getScrollWidthPx() - (getWidthPx() - getBorderLeftPx() - getBorderRightPx());
		int newLeft = between(oldLeft + delta, 0, maxLeft);
		if (oldLeft != newLeft) {
			setScrollLeft(newLeft);
			result |= EVENT_AFFECTED;
			getApplication().adjustAxis(this, AXIS_X);
		}
		return result;
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
