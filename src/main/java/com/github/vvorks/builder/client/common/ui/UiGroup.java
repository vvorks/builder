package com.github.vvorks.builder.client.common.ui;

import java.util.Objects;

import com.github.vvorks.builder.common.lang.Asserts;
import com.github.vvorks.builder.common.logging.Logger;

public class UiGroup extends UiNode {

	public static final Class<?> THIS = UiGroup.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	private Length spacingWidth;

	private Length spacingHeight;

	public UiGroup(String name) {
		super(name);
	}

	protected UiGroup(UiGroup src) {
		super(src);
		this.spacingWidth = src.spacingWidth;
		this.spacingHeight = src.spacingHeight;
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
	public void scrollFor(UiNode child) {
		Asserts.requireNotNull(child);
		Asserts.require(child.getParent() == this);
		Rect r = child.getRectangleOnParent();
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

}
