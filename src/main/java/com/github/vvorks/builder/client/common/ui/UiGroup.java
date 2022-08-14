package com.github.vvorks.builder.client.common.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.github.vvorks.builder.shared.common.lang.Asserts;

public class UiGroup extends UiNode implements Scrollable, Scrollable.Listener {

	public static final Scrollable VOID_SCROLLABLE = new Scrollable() {
		public void addScrollableListener(Scrollable.Listener listener) {/*NOP*/}
		public void removeScrollableListener(Scrollable.Listener listener) {/*NOP*/}
		public int setHorizontalScroll(int offset) { return EVENT_IGNORED; }
		public int setVerticalScroll(int offset) { return EVENT_IGNORED; }
	};

	private Scrollable masterGroup;

	private Length spacingWidth;

	private Length spacingHeight;

	/** スクローラブルリスナーのリスト */
	private List<Scrollable.Listener> scrollableListeners;

	public UiGroup(String name) {
		super(name);
		this.masterGroup = VOID_SCROLLABLE;
	}

	public UiGroup(String name, Scrollable masterGroup) {
		super(name);
		this.masterGroup = masterGroup;
		masterGroup.addScrollableListener(this);
	}

	protected UiGroup(UiGroup src) {
		super(src);
		this.spacingWidth = src.spacingWidth;
		this.spacingHeight = src.spacingHeight;
		this.masterGroup = src.masterGroup;
	}

	@Override
	public UiGroup copy() {
		return new UiGroup(this);
	}

	public Length getSpacingWidth() {
		return spacingWidth;
	}

	public int getSpacingWidthPx() {
		int result;
		if (spacingWidth != null) {
			result = spacingWidth.px(() -> getParentWidthPx());
		} else {
			result = 0;
		}
		return result;
	}

	public void setSpacingWidth(String value) {
		setSpacingWidth(value == null ? null : new Length(value));
	}

	public void setSpacingWidth(int value) {
		setSpacingWidth(new Length(value));
	}

	public void setSpacingWidth(Length newValue) {
		if (!Objects.equals(spacingWidth, newValue)) {
			spacingWidth = newValue;
			setChanged(CHANGED_CONTENT);
		}
	}

	public Length getSpacingHeight() {
		return spacingHeight;
	}

	public int getSpacingHeightPx() {
		int result;
		if (spacingHeight != null) {
			result = spacingHeight.px(() -> getParentHeightPx());
		} else {
			result = 0;
		}
		return result;
	}

	public void setSpacingHeight(String value) {
		setSpacingHeight(value == null ? null : new Length(value));
	}

	public void setSpacingHeight(int value) {
		setSpacingHeight(new Length(value));
	}

	public void setSpacingHeight(Length newValue) {
		if (!Objects.equals(spacingHeight, newValue)) {
			spacingHeight = newValue;
			setChanged(CHANGED_CONTENT);
		}
	}

	public void setSpacing(String value) {
		setSpacing(value == null ? null : new Length(value));
	}

	public void setSpacing(Length value) {
		setSpacingWidth(value);
		setSpacingHeight(value);
	}

	@Override
	public void setRelated(UiNode related) {
		super.setRelated(related);
		masterGroup.removeScrollableListener(this);
		masterGroup = (related instanceof Scrollable) ? (Scrollable) related : VOID_SCROLLABLE;
		masterGroup.addScrollableListener(this);
	}

	@Override
	public void onMount() {
		adjustScrollSize();
		super.onMount();
	}

	private void adjustScrollSize() {
		int maxWidth = 0;
		int maxHeight  = 0;
		for (UiNode child : getChildrenIf(c -> !c.isDeleted() && c.isVisible())) {
			if (child.getWidth() != null) {
				maxWidth = Math.max(maxWidth, child.getLeftPx() + child.getWidthPx());
			}
			if (child.getHeight() != null) {
				maxHeight = Math.max(maxHeight, child.getTopPx() + child.getHeightPx());
			}
		}
		if (maxWidth > 0) {
			maxWidth += getSpacingWidthPx();
			setScrollWidth(maxWidth);
		}
		if (maxHeight > 0) {
			maxHeight += getSpacingHeightPx();
			setScrollHeight(maxHeight);
		}
	}

	@Override
	public void scrollFor(UiNode target) {
		Asserts.requireNotNull(target);
		Rect r = target.getRectangleOn(this);
		Rect s = this.getRectangleOnThis();
		int dx;
		int dy;
		if (r.getLeft() < s.getLeft()) {
			dx = -(s.getLeft() - r.getLeft() + getSpacingWidthPx());
		} else if (r.getRight() > s.getRight()) {
			dx = +(r.getRight() - s.getRight() + getSpacingWidthPx());
		} else {
			dx = 0;
		}
		if (r.getTop() < s.getTop()) {
			dy = -(s.getTop() - r.getTop() + getSpacingHeightPx());
		} else if (r.getBottom() > s.getBottom()) {
			dy = +(r.getBottom() - s.getBottom() + getSpacingHeightPx());
		} else {
			dy = 0;
		}
		setScrollLeft(s.getLeft() + dx);
		setScrollTop (s.getTop()  + dy);
	}

	@Override
	public void addScrollableListener(Scrollable.Listener listener) {
		if (scrollableListeners == null) {
			scrollableListeners = new ArrayList<>();
		}
		scrollableListeners.add(listener);
	}

	@Override
	public void removeScrollableListener(Scrollable.Listener listener) {
		if (scrollableListeners == null) {
			return;
		}
		scrollableListeners.remove(listener);
	}

	@Override
	protected void notifyHorizontalScroll(int offset, int limit, int count) {
		for (Scrollable.Listener l : getScrollableListeners()) {
			l.onHorizontalScroll(this, offset, limit, count);
		}
		masterGroup.setHorizontalScroll(offset);
	}

	@Override
	protected void notifyVerticalScroll(int offset, int limit, int count) {
		for (Scrollable.Listener l : getScrollableListeners()) {
			l.onVerticalScroll(this, offset, limit, count);
		}
		masterGroup.setVerticalScroll(offset);
	}

	private List<Scrollable.Listener> getScrollableListeners() {
		if (scrollableListeners == null) {
			return Collections.emptyList();
		}
		return scrollableListeners;
	}

	@Override
	public int setHorizontalScroll(int offset) {
		int result = EVENT_IGNORED;
		int limit = getInnerWidthPx();
		int count = getScrollWidthPx();
		offset = between(offset, 0, count - limit);
		if (offset != getScrollLeftPx()) {
			setScrollLeft(offset);
			result = EVENT_AFFECTED;
		}
		return result;
	}

	@Override
	public int setVerticalScroll(int offset) {
		int result = EVENT_IGNORED;
		int limit = getInnerHeightPx();
		int count = getScrollHeightPx();
		offset = between(offset, 0, count - limit);
		if (offset != getScrollTopPx()) {
			setScrollTop(offset);
			result = EVENT_AFFECTED;
		}
		return result;
	}

	@Override
	public void onHorizontalScroll(UiNode node, int offset, int limit, int count) {
		setHorizontalScroll(offset);
	}

	@Override
	public void onVerticalScroll(UiNode node, int offset, int limit, int count) {
		setVerticalScroll(offset);
	}

}
