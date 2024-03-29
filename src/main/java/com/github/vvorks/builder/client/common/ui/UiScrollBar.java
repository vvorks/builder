package com.github.vvorks.builder.client.common.ui;

public abstract class UiScrollBar extends UiNode implements Scrollable.Listener {

	/** スクロールバー内部のつまみのノード名称（スタイル設定時に使用する） */
	public static final String THUMB_NAME = "thumb";

	protected static class UiThumb extends UiNode {

		public UiThumb() {
			super(THUMB_NAME);
		}

		public UiThumb(UiThumb src) {
			super(src);
		}

		@Override
		public UiThumb copy() {
			return new UiThumb(this);
		}

	}

	private Scrollable scrollable;

	private UiThumb thumbMain;

	private UiThumb thumbSub;

	protected int lastOffset;

	protected int lastLimit;

	protected int lastCount;

	protected double ratio;

	protected UiScrollBar(String name) {
		super(name);
		this.scrollable = UiGroup.VOID_SCROLLABLE;
		this.scrollable.addScrollableListener(this);
		thumbMain = (UiThumb) appendChild(createThumb());
		thumbSub = (UiThumb) appendChild(createThumb());
	}

	protected UiScrollBar(UiScrollBar src) {
		super(src);
		this.scrollable = src.scrollable;
		this.scrollable.addScrollableListener(this);
		this.thumbMain = (UiThumb) getFirstChild();
		this.thumbSub = (UiThumb) this.thumbMain.getNextSibling();
	}

	private UiThumb createThumb() {
		UiThumb thumb = new UiThumb();
		thumb.setBounds(Length.ZERO, Length.ZERO, null, null, Length.ZERO, Length.ZERO);
		thumb.setVisible(false);
		return thumb;
	}

	@Override
	public void setRelated(UiNode related) {
		super.setRelated(related);
		scrollable.removeScrollableListener(this);
		scrollable = (related instanceof Scrollable) ? (Scrollable) related : UiGroup.VOID_SCROLLABLE;
		scrollable.addScrollableListener(this);
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
		lastCount = count;
		ratio = (double) count / getWidthPx();
		if (offset + limit <= count) {
			//通常のケース
			double left  = 100.0 * offset / count;
			double width = 100.0 * limit  / count;
			thumbMain.setBounds(
					Length.pctOf(left),		Length.ZERO,
					null,					Length.ZERO,
					Length.pctOf(width),	null);
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
					Length.pctOf(left),		Length.ZERO,
					null, 					Length.ZERO,
					Length.FULL,			null);
			thumbSub.setBounds(
					Length.ZERO,			Length.ZERO,
					null,					Length.ZERO,
					Length.pctOf(remain),	null);
			thumbMain.setVisible(true);
			thumbSub.setVisible(true);
		}
	}

	@Override
	public void onVerticalScroll(UiNode node, int offset, int limit, int count) {
		lastOffset = offset;
		lastLimit = limit;
		lastCount = count;
		ratio = (double) count / getHeightPx();
		if (offset + limit <= count) {
			//通常のケース
			double top    = 100.0 * offset / count;
			double height = 100.0 * limit  / count;
			thumbMain.setBounds(
					Length.ZERO,	Length.pctOf(top),
					Length.ZERO,	null,
					null,			Length.pctOf(height));
			thumbSub.setBounds(
					Length.ZERO,	Length.ZERO,
					Length.ZERO,	null,
					null,			Length.ZERO);
			thumbMain.setVisible(true);
			thumbSub.setVisible(false);
		} else {
			//ラップケース
			double top    = 100.0 * offset / count;
			double remain = 100.0 * (offset + limit - count) / count;
			thumbMain.setBounds(
					Length.ZERO,	Length.pctOf(top),
					Length.ZERO,	null,
					null,			Length.FULL);
			thumbSub.setBounds(
					Length.ZERO,	Length.ZERO,
					Length.ZERO,	null,
					null,			Length.pctOf(remain));
			thumbMain.setVisible(true);
			thumbSub.setVisible(true);
		}
	}

}
