package com.github.vvorks.builder.client.common.ui;

import java.util.EnumSet;

import com.github.vvorks.builder.common.logging.Logger;

public class UiSplitGroup extends UiGroup {

	private static final Logger LOGGER = Logger.createLogger(UiSplitGroup.class);

	public static final String SPLITTER_NAME = "splitter";

	public enum Param implements LayoutParam {
		TOP, TOP_BORDER,
		LEFT, LEFT_BORDER,
		CENTER,
		RIGHT, RIGHT_BORDER,
		BOTTOM, BOTTOM_BORDER
	}

	private static final EnumSet<Param> BORDER_PARAM = EnumSet.of(
			Param.TOP_BORDER, Param.LEFT_BORDER, Param.RIGHT_BORDER, Param.BOTTOM_BORDER);

	private static final EnumSet<Param> H_PARAM = EnumSet.of(
			Param.TOP, Param.TOP_BORDER, Param.BOTTOM, Param.BOTTOM_BORDER);

	private static final EnumSet<Param> V_PARAM = EnumSet.of(
			Param.LEFT, Param.LEFT_BORDER, Param.RIGHT, Param.RIGHT_BORDER);

	private static class UiSplitter extends UiNode {

		public UiSplitter() {
			super(SPLITTER_NAME);
		}

		public UiSplitter(UiSplitter src) {
			super(src);
		}

		@Override
		public UiSplitter copy() {
			return new UiSplitter(this);
		}

		@Override
		protected void syncElementStyle(CssStyle.Builder b) {
			super.syncElementStyle(b);
			Param dir = (Param) getLayoutParam();
			if (H_PARAM.contains(dir)) {
				b.property("cursor", "ns-resize");
			} else if (V_PARAM.contains(dir)) {
				b.property("cursor", "ew-resize");
			}
		}
	}

	public UiSplitGroup(String name) {
		super(name);
	}

	public UiSplitGroup(UiSplitGroup src) {
		super(src);
	}

	@Override
	public UiSplitGroup copy() {
		return new UiSplitGroup(this);
	}

	@Override
	public UiNode insertChild(UiNode newChild, LayoutParam param) {
		throw new UnsupportedOperationException();
	}

	@Override
	public UiNode insertBefore(UiNode newChild, UiNode refChild, LayoutParam param) {
		if (refChild != null) {
			throw new UnsupportedOperationException();
		}
		if (!(param instanceof Param)) {
			param = Param.CENTER;
		}
		Param dir = (Param) param;
		UiNode center = getPane(Param.CENTER);
		super.insertBefore(newChild, center, dir);
		if (BORDER_PARAM.contains(dir)) {
			UiNode splitter = new UiSplitter();
			splitter.setStyle(getStyle());
			super.insertBefore(splitter, center, dir);
		}
		return newChild;
	}

	private UiNode getPane(Param dir) {
		for (UiNode pane = getFirstChild(); pane != null; pane = pane.getNextSibling()) {
			if (!(pane instanceof UiSplitter) && pane.getLayoutParam() == dir) {
				return pane;
			}
		}
		return null;
	}

	private UiSplitter getSplitter(UiNode pane) {
		UiNode next = pane.getNextSibling();
		return next instanceof UiSplitter ? (UiSplitter) next : null;
	}

	@Override
	public void onMount() {
		locatePanes();
		super.onMount();
	}

	private void locatePanes() {
		double left = 0;
		double top = 0;
		double right = 0;
		double bottom = 0;
		for (UiNode child = getFirstChild(); child != null; child = child.getNextSibling()) {
			if (!(child instanceof UiSplitter)) {
				switch ((Param) child.getLayoutParam()) {
				case TOP:
				case TOP_BORDER:
					top = locateTop(child, left, top, right, bottom);
					break;
				case BOTTOM:
				case BOTTOM_BORDER:
					bottom = locateBottom(child, left, top, right, bottom);
					break;
				case LEFT:
				case LEFT_BORDER:
					left = locateLeft(child, left, top, right, bottom);
					break;
				case RIGHT:
				case RIGHT_BORDER:
					right = locateRight(child, left, top, right, bottom);
					break;
				case CENTER:
					child.setBounds(
							Length.pxOf(left), Length.pxOf(top),
							Length.pxOf(right), Length.pxOf(bottom),
							null, null);
					break;
				default:
					break;
				}
			}
		}
	}

	private double locateTop(UiNode pane, double left, double top, double right, double bottom) {
		double height = pane.getHeightPx();
		pane.setBounds(
				Length.pxOf(left), Length.pxOf(top),
				Length.pxOf(right), null,
				null, Length.pxOf(height));
		top += height;
		UiSplitter splitter = getSplitter(pane);
		if (splitter != null) {
			splitter.setBounds(
					Length.pxOf(left), Length.pxOf(top),
					Length.pxOf(right), null,
					null, getSpacingHeight());
			top += getSpacingHeightPx();
		}
		return top;
	}

	private double locateBottom(UiNode pane, double left, double top, double right, double bottom) {
		double height = pane.getHeightPx();
		pane.setBounds(
				Length.pxOf(left), null,
				Length.pxOf(right), Length.pxOf(bottom),
				null, Length.pxOf(height));
		bottom += height;
		UiSplitter splitter = getSplitter(pane);
		if (splitter != null) {
			splitter.setBounds(
					Length.pxOf(left), null,
					Length.pxOf(right), Length.pxOf(bottom),
					null, getSpacingHeight());
			bottom += getSpacingHeightPx();
		}
		return bottom;
	}

	private double locateLeft(UiNode pane, double left, double top, double right, double bottom) {
		double width = pane.getWidthPx();
		pane.setBounds(
				Length.pxOf(left), Length.pxOf(top),
				null, Length.pxOf(bottom),
				Length.pxOf(width), null);
		left += width;
		UiSplitter splitter = getSplitter(pane);
		if (splitter != null) {
			splitter.setBounds(
					Length.pxOf(left), Length.pxOf(top),
					null, Length.pxOf(bottom),
					getSpacingWidth(), null);
			left += getSpacingWidthPx();
		}
		return left;
	}

	private double locateRight(UiNode pane, double left, double top, double right, double bottom) {
		double width = pane.getWidthPx();
		pane.setBounds(
				null, Length.pxOf(top),
				Length.pxOf(right), Length.pxOf(bottom),
				Length.pxOf(width), null);
		right += width;
		UiSplitter splitter = getSplitter(pane);
		if (splitter != null) {
			splitter.setBounds(
					null, Length.pxOf(top),
					Length.pxOf(right), Length.pxOf(bottom),
					getSpacingWidth(), null);
			right += getSpacingWidthPx();
		}
		return right;
	}

	@Override
	public int onMouseMove(UiNode target, int x, int y, int mods, int time) {
		if ((mods & KeyCodes.MOD_LBUTTON) != 0) {
			LOGGER.debug("onMouseDrag %s", target.getName());
		}
		return super.onMouseMove(target, x, y, mods, time);
	}

	@Override
	public int onMouseDown(UiNode target, int x, int y, int mods, int time) {
		LOGGER.debug("onMouseDown %s", target.getName());
		return super.onMouseDown(target, x, y, mods, time);
	}

	@Override
	public int onMouseUp(UiNode target, int x, int y, int mods, int time) {
		LOGGER.debug("onMouseUp %s", target.getName());
		return super.onMouseUp(target, x, y, mods, time);
	}

	@Override
	public int onMouseClick(UiNode target, int x, int y, int mods, int time) {
		// TODO 自動生成されたメソッド・スタブ
		return super.onMouseClick(target, x, y, mods, time);
	}



}
