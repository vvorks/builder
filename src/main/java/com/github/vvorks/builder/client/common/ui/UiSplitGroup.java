package com.github.vvorks.builder.client.common.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UiSplitGroup extends UiGroup {

	private static final int MIN_ELDER_SIZE = 4;

	public static final String SPLITTER_NAME = "splitter";

	public enum Param implements LayoutParam {

		TOP("ns-resize", true), BOTTOM("ns-resize", false),
		LEFT("ew-resize", true), RIGHT("ew-resize", false),
		CENTER("default", true);

		private final String cursorType;

		private final boolean forward;

		private Param(String cursorType, boolean forward) {
			this.cursorType = cursorType;
			this.forward = forward;
		}

		public String getCursorType() {
			return cursorType;
		}

		public boolean isSameType(Param p) {
			return cursorType.equals(((Param)p).cursorType);
		}

		public boolean isForward() {
			return forward;
		}

	}

	private static class UiSplitter extends UiNode {

		private final UiNode elderPane;

		private List<UiNode> youngerPanes;

		private int elderSize;

		public UiSplitter(UiNode elderPane) {
			super(SPLITTER_NAME);
			this.elderPane = elderPane;
		}

		public UiSplitter(UiSplitter src) {
			super(src);
			this.elderPane = src.elderPane;
		}

		@Override
		public UiSplitter copy() {
			return new UiSplitter(this);
		}

		@Override
		protected void syncElementStyle(CssStyle.Builder b) {
			super.syncElementStyle(b);
			b.property("cursor", getParam().getCursorType());
		}

		@Override
		public String getDisplayName() {
			return elderPane.getName() + "-" + getName();
		}

		public UiNode getElderPane() {
			return elderPane;
		}

		public List<UiNode> getYoungerPanes() {
			return youngerPanes;
		}

		public void setYoungerPanes(List<UiNode> youngerPanes) {
			this.youngerPanes = youngerPanes;
		}

		public Param getParam() {
			return (Param) getLayoutParam();
		}

		public int getElderSize() {
			return this.elderSize;
		}

		public void setElderSize(int size) {
			this.elderSize = size;
		}

	}

	private UiSplitter downSplitter;

	private int xDown;

	private int yDown;

	private int wDown;

	private int hDown;

	private boolean bDragged;

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
		UiNode center = findPaneBy(Param.CENTER);
		super.insertBefore(newChild, center, dir);
		if (dir != Param.CENTER) {
			UiNode splitter = new UiSplitter(newChild);
			splitter.setStyle(getStyle());
			super.insertBefore(splitter, center, dir);
		}
		return newChild;
	}

	private UiNode findPaneBy(Param dir) {
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
		collectYoungerPanes();
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
					top = locateTop(child, left, top, right, bottom);
					break;
				case BOTTOM:
					bottom = locateBottom(child, left, top, right, bottom);
					break;
				case LEFT:
					left = locateLeft(child, left, top, right, bottom);
					break;
				case RIGHT:
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
		int height = pane.getHeightPx();
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
			splitter.setElderSize(height);
		}
		return top;
	}

	private double locateBottom(UiNode pane, double left, double top, double right, double bottom) {
		int height = pane.getHeightPx();
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
			splitter.setElderSize(height);
		}
		return bottom;
	}

	private double locateLeft(UiNode pane, double left, double top, double right, double bottom) {
		int width = pane.getWidthPx();
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
			splitter.setElderSize(width);
		}
		return left;
	}

	private double locateRight(UiNode pane, double left, double top, double right, double bottom) {
		int width = pane.getWidthPx();
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
			splitter.setElderSize(width);
		}
		return right;
	}

	private void collectYoungerPanes() {
		for (UiNode child = getFirstChild(); child != null; child = child.getNextSibling()) {
			if (child instanceof UiSplitter) {
				UiSplitter splitter = (UiSplitter) child;
				switch (splitter.getParam()) {
				case TOP:
					splitter.setYoungerPanes(collectYoungerTop(splitter));
					break;
				case BOTTOM:
					splitter.setYoungerPanes(collectYoungerBottom(splitter));
					break;
				case LEFT:
					splitter.setYoungerPanes(collectYoungerLeft(splitter));
					break;
				case RIGHT:
					splitter.setYoungerPanes(collectYoungerRight(splitter));
					break;
				default:
					throw new AssertionError();
				}
			}
		}
	}

	private List<UiNode> collectYoungerTop(UiSplitter splitter) {
		int height = this.getHeightPx();
		int nextEdge = height - splitter.getBottomPx();
		int left = splitter.getLeftPx();
		int right = splitter.getRightPx();
		Param dir = splitter.getParam();
		List<UiNode> youngers = new ArrayList<>();
		for (UiNode child = splitter.getNextSibling(); child != null; child = child.getNextSibling()) {
			int childEdge = child.getTopPx();
			int childLeft = child.getLeftPx();
			int childRight = child.getRightPx();
			if (left <= childLeft && childRight >= right && childEdge == nextEdge) {
				if (!(child instanceof UiSplitter && dir.isSameType(((UiSplitter)child).getParam()))) {
					youngers.add(child);
				}
			}
		}
		return youngers;
	}

	private List<UiNode> collectYoungerBottom(UiSplitter splitter) {
		int height = this.getHeightPx();
		int nextEdge = splitter.getTopPx();
		int left = splitter.getLeftPx();
		int right = splitter.getRightPx();
		Param dir = splitter.getParam();
		List<UiNode> youngers = new ArrayList<>();
		for (UiNode child = splitter.getNextSibling(); child != null; child = child.getNextSibling()) {
			int childEdge = height - child.getBottomPx();
			int childLeft = child.getLeftPx();
			int childRight = child.getRightPx();
			if (left <= childLeft && childRight >= right && childEdge == nextEdge) {
				if (!(child instanceof UiSplitter && dir.isSameType(((UiSplitter)child).getParam()))) {
					youngers.add(child);
				}
			}
		}
		return youngers;
	}

	private List<UiNode> collectYoungerLeft(UiSplitter splitter) {
		int width = this.getWidthPx();
		int nextEdge = width - splitter.getRightPx();
		int top = splitter.getTopPx();
		int bottom = splitter.getBottomPx();
		Param dir = splitter.getParam();
		List<UiNode> youngers = new ArrayList<>();
		for (UiNode child = splitter.getNextSibling(); child != null; child = child.getNextSibling()) {
			int childEdge = child.getLeftPx();
			int childTop = child.getTopPx();
			int childBottom = child.getBottomPx();
			if (top <= childTop && childBottom >= bottom && childEdge == nextEdge) {
				if (!(child instanceof UiSplitter && dir.isSameType(((UiSplitter)child).getParam()))) {
					youngers.add(child);
				}
			}
		}
		return youngers;
	}

	private List<UiNode> collectYoungerRight(UiSplitter splitter) {
		int width = this.getWidthPx();
		int nextEdge = splitter.getLeftPx();
		int top = splitter.getTopPx();
		int bottom = splitter.getBottomPx();
		Param dir = splitter.getParam();
		List<UiNode> youngers = new ArrayList<>();
		for (UiNode child = splitter.getNextSibling(); child != null; child = child.getNextSibling()) {
			int childEdge = width - child.getRightPx();
			int childTop = child.getTopPx();
			int childBottom = child.getBottomPx();
			if (top <= childTop && childBottom >= bottom && childEdge == nextEdge) {
				if (!(child instanceof UiSplitter && dir.isSameType(((UiSplitter)child).getParam()))) {
					youngers.add(child);
				}
			}
		}
		return youngers;
	}

	@Override
	public int onMouseDown(UiNode target, int x, int y, int mods, int time) {
		int result = EVENT_IGNORED;
		UiApplication app = getApplication();
		if (target instanceof UiSplitter) {
			downSplitter = (UiSplitter) target;
			xDown = x;
			yDown = y;
			UiNode elder = downSplitter.getElderPane();
			wDown = elder.getWidthPx();
			hDown = elder.getHeightPx();
			bDragged = false;
			app.setCapture(this);
			result = EVENT_CONSUMED;
		}
		return result;
	}

	@Override
	public int onMouseMove(UiNode target, int x, int y, int mods, int time) {
		int result = EVENT_IGNORED;
		if (downSplitter != null) {
			int dx = x - xDown;
			int dy = y - yDown;
			bDragged = true;
			switch (downSplitter.getParam()) {
			case TOP:
				result = resizeTop(downSplitter, hDown + dy, true);
				break;
			case BOTTOM:
				result = resizeBottom(downSplitter, hDown - dy, true);
				break;
			case LEFT:
				result = resizeLeft(downSplitter, wDown + dx, true);
				break;
			case RIGHT:
				result = resizeRight(downSplitter, wDown - dx, true);
				break;
			default:
				throw new AssertionError();
			}
		}
		return result;
	}

	private int resizeTop(UiSplitter splitter, int elderSize, boolean updateSize) {
		int totalHeight = this.getHeightPx();
		UiNode elder = splitter.getElderPane();
		int spacing = getSpacingHeightPx();
		int minPos = elder.getTopPx();
		int maxPos = totalHeight;
		for (UiNode younger : splitter.getYoungerPanes()) {
			maxPos = Math.min(maxPos, (totalHeight - younger.getBottomPx()) - spacing);
		}
		int newPos = Math.min(Math.max(minPos, minPos + elderSize), maxPos);
		setHorizontal(elder   , minPos, newPos);
		setHorizontal(splitter, newPos, newPos + spacing);
		int newSize = newPos - minPos;
		if (updateSize && newSize >= MIN_ELDER_SIZE) {
			splitter.setElderSize(newSize);
		}
		newPos += spacing;
		for (UiNode younger : splitter.getYoungerPanes()) {
			setHorizontal(younger, newPos, totalHeight - younger.getBottomPx());
		}
		return EVENT_EATEN;
	}

	private int resizeBottom(UiSplitter splitter, int elderSize, boolean updateSize) {
		int totalHeight = this.getHeightPx();
		UiNode elder = splitter.getElderPane();
		int spacing = getSpacingHeightPx();
		int maxPos = totalHeight - elder.getBottomPx();
		int minPos = spacing;
		for (UiNode younger : splitter.getYoungerPanes()) {
			minPos = Math.max(minPos, younger.getTopPx() + spacing);
		}
		int newPos = Math.min(Math.max(minPos, maxPos - elderSize), maxPos);
		setHorizontal(elder   , newPos, maxPos);
		setHorizontal(splitter, newPos - spacing, newPos);
		int newSize = maxPos - newPos;
		if (updateSize && newSize >= MIN_ELDER_SIZE) {
			splitter.setElderSize(newSize);
		}
		newPos -= spacing;
		for (UiNode younger : splitter.getYoungerPanes()) {
			setHorizontal(younger, younger.getTopPx(), newPos);
		}
		return EVENT_EATEN;
	}

	private void setHorizontal(UiNode node, int sy, int ey) {
		int totalHeight = this.getHeightPx();
		if (node.getTop() != null) {
			node.setTop(sy);
		}
		if (node.getHeight() != null) {
			node.setHeight(ey - sy);
		}
		if (node.getBottom() != null) {
			node.setBottom(totalHeight - ey);
		}
	}

	private int resizeLeft(UiSplitter splitter, int elderSize, boolean updateSize) {
		int totalWidth = this.getWidthPx();
		UiNode elder = splitter.getElderPane();
		int spacing = getSpacingWidthPx();
		int minPos = elder.getLeftPx();
		int maxPos = totalWidth;
		for (UiNode c : splitter.getYoungerPanes()) {
			maxPos = Math.min(maxPos, (totalWidth - c.getRightPx()) - spacing);
		}
		int newPos = Math.min(Math.max(minPos, minPos + elderSize), maxPos);
		setVertical(elder   , minPos, newPos);
		setVertical(splitter, newPos, newPos + spacing);
		int newSize = newPos - minPos;
		if (updateSize && newSize >= MIN_ELDER_SIZE) {
			splitter.setElderSize(newSize);
		}
		newPos += spacing;
		for (UiNode younger : splitter.getYoungerPanes()) {
			setVertical(younger, newPos, totalWidth - younger.getRightPx());
		}
		return EVENT_EATEN;
	}

	private int resizeRight(UiSplitter splitter, int elderSize, boolean updateSize) {
		int totalWidth = this.getWidthPx();
		UiNode elder = splitter.getElderPane();
		int spacing = getSpacingWidthPx();
		int maxPos = totalWidth - elder.getRightPx();
		int minPos = spacing;
		for (UiNode younger : splitter.getYoungerPanes()) {
			minPos = Math.max(minPos, younger.getLeftPx() + spacing);
		}
		int newPos = Math.min(Math.max(minPos, maxPos - elderSize), maxPos);
		setVertical(elder   , newPos, maxPos);
		setVertical(splitter, newPos - spacing, newPos);
		int newSize = maxPos - newPos;
		if (updateSize && newSize >= MIN_ELDER_SIZE) {
			splitter.setElderSize(newSize);
		}
		newPos -= spacing;
		for (UiNode younger : splitter.getYoungerPanes()) {
			setVertical(younger, younger.getLeftPx(), newPos);
		}
		return EVENT_EATEN;
	}

	private void setVertical(UiNode node, int sx, int ex) {
		int totalWidth = this.getWidthPx();
		if (node.getLeft() != null) {
			node.setLeft(sx);
		}
		if (node.getWidth() != null) {
			node.setWidth(ex - sx);
		}
		if (node.getRight() != null) {
			node.setRight(totalWidth - ex);
		}
	}

	@Override
	public int onMouseUp(UiNode target, int x, int y, int mods, int time) {
		int result = EVENT_IGNORED;
		UiApplication app = getApplication();
		if (downSplitter != null) {
			if (!bDragged) {
				int elderSize = downSplitter.getElderSize();
				int elderWidth  = wDown != 0 ? 0 : elderSize;
				int elderHeight = hDown != 0 ? 0 : elderSize;
				switch (downSplitter.getParam()) {
				case TOP:
					result = resizeTop(downSplitter, elderHeight, false);
					break;
				case BOTTOM:
					result = resizeBottom(downSplitter, elderHeight, false);
					break;
				case LEFT:
					result = resizeLeft(downSplitter, elderWidth, false);
					break;
				case RIGHT:
					result = resizeRight(downSplitter, elderWidth, false);
					break;
				default:
					throw new AssertionError();
				}
			}
			bDragged = false;
			downSplitter = null;
			app.releaseCapture(this);
		}
		return result;
	}

	@Override
	public UiNode getFirstChild(int visitOrder) {
		if (visitOrder != VISIT_FOCUS_ORDER) {
			return super.getFirstChild(visitOrder);
		}
		List<UiNode> list = getPanesByFocus();
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public UiNode getNextChild(UiNode child, int visitOrder) {
		if (visitOrder != VISIT_FOCUS_ORDER) {
			return super.getNextChild(child, visitOrder);
		}
		List<UiNode> list = getPanesByFocus();
		int index = list.indexOf(child) + 1;
		return 0 < index && index < list.size() ? list.get(index) : null;
	}

	private List<UiNode> getPanesByFocus() {
		List<UiNode> headList = new ArrayList<>();
		List<UiNode> tailList = new ArrayList<>();
		for (UiNode c = getFirstChild(); c != null; c = c.getNextSibling()) {
			if (!(c instanceof UiSplitter)) {
				if (((Param)c.getLayoutParam()).isForward()) {
					headList.add(c);
				} else {
					tailList.add(c);
				}
			}
		}
		Collections.reverse(tailList);
		headList.addAll(tailList);
		return headList;
	}

}
