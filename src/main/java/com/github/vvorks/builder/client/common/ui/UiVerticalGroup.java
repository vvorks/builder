package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.client.ClientSettings;
import com.github.vvorks.builder.common.logging.Logger;

public class UiVerticalGroup extends UiGroup {

	public static final Class<?> THIS = UiVerticalGroup.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	public UiVerticalGroup(String name) {
		super(name);
	}

	protected UiVerticalGroup(UiGroup src) {
		super(src);
	}

	@Override
	public UiVerticalGroup copy() {
		return new UiVerticalGroup(this);
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
		int oldTop = getScrollTopPx();
		int maxTop = getScrollHeightPx() - (getHeightPx() - getBorderTopPx() - getBorderBottomPx());
		int newTop = Math.min(Math.max(0, oldTop + delta), maxTop);
		if (oldTop != newTop) {
			setScrollTop(newTop);
			result |= EVENT_AFFECTED;
			getApplication().adjustAxis(UiApplication.AXIS_Y);
		}
		return result;
	}

	private void relocateChildren() {
		int spc = getSpacingHeightPx();
		int total = spc;
		for (UiNode child : getChildrenIf(c -> !c.isDeleted() && c.isVisible())) {
			Length top = new Length(total);
			Length height = child.getHeight();
			Length left = child.getLeft();
			Length right = child.getRight();
			Length width = child.getWidth();
			if (left == null && right == null && width == null) {
				left = right = getSpacingWidth();
			}
			child.setBounds(left, top, right, null, width, height);
			total += child.getHeightPx() + spc;
		}
		setScrollHeight(total);
	}

}
