package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.client.ClientSettings;
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

	@Override
	public int onMouseWheel(UiNode target, int x, int y, int dx, int dy, int mods, int time) {
		int result = EVENT_CONSUMED;
		int delta = dy * ClientSettings.WHEEL_SCALE;
		int oldLeft = getScrollLeftPx();
		int maxLeft = getScrollWidthPx() - (getWidthPx() - getBorderLeftPx() - getBorderRightPx());
		int newLeft = Math.min(Math.max(0, oldLeft + delta), maxLeft);
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
