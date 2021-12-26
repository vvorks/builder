package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.client.common.ui.Length.Unit;
import com.github.vvorks.builder.common.logging.Logger;

public abstract class UiScrollBar extends UiNode implements ScrollListener {

	private static final Logger LOGGER = Logger.createLogger(UiScrollBar.class);

	protected static final Scrollable DUMMY_SCROLLABLE = new Scrollable() {
		public int setHorizontalScroll(int offset) { return EVENT_IGNORED; }
		public int setVerticalScroll(int offset) { return EVENT_IGNORED; }
	};

	protected static class UiThumb extends UiNode {

		public UiThumb() {
			super("thumb");
		}

		@Override
		public UiThumb copy() {
			throw new UnsupportedOperationException();
		}
	}

	private Scrollable scrollable;

	private UiThumb thumbMain;

	private UiThumb thumbSub;

	protected int lastOffset;

	protected int lastLimit;

	protected double ratio;

	public UiScrollBar(String name, UiNode scrollable) {
		super(name);
		if (scrollable != null) {
			this.scrollable = scrollable;
		} else {
			this.scrollable = DUMMY_SCROLLABLE;
		}
		this.scrollable.addScrollListener(this);
		thumbMain = (UiThumb) appendChild(createThumb());
		thumbSub = (UiThumb) appendChild(createThumb());
	}

	protected UiScrollBar(UiScrollBar src) {
		super(src);
		this.scrollable = src.scrollable;
		this.scrollable.addScrollListener(this);
	}

	private UiThumb createThumb() {
		UiThumb thumb = new UiThumb();
		thumb.setBounds(Length.ZERO, Length.ZERO, null, null, Length.ZERO, Length.ZERO);
		thumb.setVisible(false);
		return thumb;
	}

	@Override
	public void setStyle(UiStyle style) {
		super.setStyle(style);
		thumbMain.setStyle(style);
		thumbSub.setStyle(style);
	}

	protected int invokeHorizontalScroll(int offset) {
		return scrollable.setHorizontalScroll(offset);
	}

	protected int invokeVerticalScroll(int offset) {
		return scrollable.setVerticalScroll(offset);
	}

	protected boolean isThumbNode(UiNode node) {
		return node == thumbMain || node == thumbSub;
	}

	protected Rect getThumbRect() {
		return thumbMain.getRectangleOnParent();
	}

	@Override
	public void onFocus(UiNode target, boolean gained, UiNode other) {
		super.onFocus(target, gained, other);
		thumbMain.setChanged(CHANGED_DISPLAY);
		thumbSub.setChanged(CHANGED_DISPLAY);
	}

	@Override
	public void onHorizontalScroll(UiNode node, int offset, int limit, int count) {
		lastOffset = offset;
		lastLimit = limit;
		ratio = (double) count / getWidthPx();
		if (offset + limit <= count) {
			//通常のケース
			double left  = 100.0 * offset / count;
			double width = 100.0 * limit  / count;
			thumbMain.setBounds(
					Unit.PCT.of(left),	Length.ZERO,
					null,				Length.ZERO,
					Unit.PCT.of(width),	null);
			thumbSub.setBounds(
					Length.ZERO, Length.ZERO,
					null,		 Length.ZERO,
					Length.ZERO, null);
			thumbMain.setVisible(true);
			thumbSub.setVisible(false);
		} else {
			//ラップケース
			double left   = 100.0 * offset / count;
			double remain = 100.0 * (offset + limit - count) / count;
			thumbMain.setBounds(
					Unit.PCT.of(left),	Length.ZERO,
					null, 				Length.ZERO,
					Length.FULL,		null);
			thumbSub.setBounds(
					Length.ZERO,		Length.ZERO,
					 null,				Length.ZERO,
					 Unit.PCT.of(remain), null);
			thumbMain.setVisible(true);
			thumbSub.setVisible(true);
		}
	}

	@Override
	public void onVerticalScroll(UiNode node, int offset, int limit, int count) {
		lastOffset = offset;
		lastLimit = limit;
		ratio = (double) count / getHeightPx();
		if (offset + limit <= count) {
			//通常のケース
			double top    = 100.0 * offset / count;
			double height = 100.0 * limit  / count;
			thumbMain.setBounds(
					Length.ZERO, Unit.PCT.of(top),
					Length.ZERO, null,
					null,		 Unit.PCT.of(height));
			thumbSub.setBounds(
					Length.ZERO, Length.ZERO,
					Length.ZERO, null,
					null,		 Length.ZERO);
			thumbMain.setVisible(true);
			thumbSub.setVisible(false);
		} else {
			//ラップケース
			double top    = 100.0 * offset / count;
			double remain = 100.0 * (offset + limit - count) / count;
			thumbMain.setBounds(
					Length.ZERO, Unit.PCT.of(top),
					Length.ZERO, null,
					null,		 Length.FULL);
			thumbSub.setBounds(
					Length.ZERO, Length.ZERO,
					Length.ZERO, null,
					null,		 Unit.PCT.of(remain));
			thumbMain.setVisible(true);
			thumbSub.setVisible(true);
		}
	}

}
