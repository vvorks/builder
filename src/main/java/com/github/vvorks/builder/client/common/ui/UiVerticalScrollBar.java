package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.client.ClientSettings;
import com.github.vvorks.builder.client.common.ui.Length.Unit;
import com.github.vvorks.builder.common.logging.Logger;

public class UiVerticalScrollBar extends UiNode implements ScrollListener {

	private static final Logger LOGGER = Logger.createLogger(UiVerticalScrollBar.class);

	private static final Scrollable DUMMY_SCROLLABLE = new Scrollable() {
		public int setHorizontalScroll(int offset) { return EVENT_IGNORED; }
		public int setVerticalScroll(int offset) { return EVENT_IGNORED; }
	};

	private static class UiThumb extends UiNode {

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

	private int lastOffset;

	private int lastLimit;

	private double ratio;

	/** ドラッグ中のThumb */
	private UiThumb thumbDown;

	private int offsetDown;

	private int yDown;

	public UiVerticalScrollBar(String name, UiNode scrollable) {
		super(name);
		if (scrollable != null) {
			this.scrollable = scrollable;
		} else {
			this.scrollable = DUMMY_SCROLLABLE;
		}
		this.scrollable.addScrollListener(this);
		thumbMain = (UiThumb) appendChild(createThumb());
		thumbSub = (UiThumb) appendChild(createThumb());
		thumbDown = null;
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

	@Override
	public void onFocus(UiNode target, boolean gained, UiNode other) {
		super.onFocus(target, gained, other);
		thumbMain.setChanged(CHANGED_DISPLAY);
		thumbSub.setChanged(CHANGED_DISPLAY);
	}

	protected UiVerticalScrollBar(UiVerticalScrollBar src) {
		super(src);
		this.scrollable = src.scrollable;
		this.scrollable.addScrollListener(this);
	}

	@Override
	public UiVerticalScrollBar copy() {
		return new UiVerticalScrollBar(this);
	}

	@Override
	public void onHorizontalScroll(UiNode node, int offset, int limit, int count) {
		//NOP
	}

	@Override
	public void onVerticalScroll(UiNode node, int offset, int limit, int count) {
		LOGGER.debug("onVerticalScroll(%s, %d, %d, %d)", node.getName(), offset, limit, count);
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

	@Override
	public int onKeyDown(UiNode target, int keyCode, int charCode, int mods, int time) {
		int result;
		switch (keyCode) {
		case KeyCodes.UP:
			LOGGER.debug("** %d - %d", lastOffset, lastLimit);
			result = scrollable.setVerticalScroll(lastOffset - lastLimit);
			break;
		case KeyCodes.DOWN:
			LOGGER.debug("** %d + %d", lastOffset, lastLimit);
			result = scrollable.setVerticalScroll(lastOffset + lastLimit);
			break;
		default:
			result = EVENT_IGNORED;
			break;
		}
		return result;
	}

	@Override
	public int onMouseDown(UiNode target, int x, int y, int mods, int time) {
		int result = EVENT_IGNORED;
		UiApplication app = getApplication();
		if (target == thumbMain || target == thumbSub) {
			LOGGER.trace("SB DOWN 2");
			//main又はsub thum上でのDOWN→THUMBグラブ
			thumbDown = (UiThumb) target;
			offsetDown = lastOffset;
			yDown = y;
			app.setCapture(thumbDown);
		} else if (target == this) {
			//SB上でのDOWN→上又は下へのスクロール
			int thumbHead = thumbMain.getTopPx();
			int thumbTail = thumbHead + thumbMain.getWidthPx();
			if (y < thumbHead) {
				result = scrollable.setVerticalScroll(lastOffset - lastLimit);
			} else if (thumbTail <= y) {
				result = scrollable.setVerticalScroll(lastOffset + lastLimit);
			}
			thumbDown = null;
		}
		app.setFocus(this);
		return result;
	}

	@Override
	public int onMouseMove(UiNode target, int x, int y, int mods, int time) {
		if ((mods & KeyCodes.MOD_LBUTTON) == 0) {
			return super.onMouseMove(target, x, y, mods, time);
		}
		int result = EVENT_IGNORED;
		if (thumbDown != null) {
			//main又はsub thum上でのDOWN→THUMBグラブ
			int delta = (int) Math.round(ratio * (y - yDown));
			result = scrollable.setVerticalScroll(offsetDown + delta);
		} else if (target == this) {
			//SB上でのDRAG又は押し続け→上又は下へのスクロール
			int thumbHead = thumbMain.getTopPx();
			int thumbTail = thumbHead + thumbMain.getWidthPx();
			if (y < thumbHead) {
				result = scrollable.setVerticalScroll(lastOffset - lastLimit);
			} else if (thumbTail <= y) {
				result = scrollable.setVerticalScroll(lastOffset + lastLimit);
			}
		}
		return result;
	}

	@Override
	public int onMouseUp(UiNode target, int x, int y, int mods, int time) {
		int result = EVENT_IGNORED;
		LOGGER.trace("SB UP 0");
		if (thumbDown != null) {
			int delta = (int) Math.round(ratio * (y - yDown));
			result = scrollable.setVerticalScroll(offsetDown + delta);
			getApplication().releaseCapture();
		} else if (target == this) {
			//NOP
		}
		return result;
	}

	@Override
	public int onMouseWheel(UiNode target, int x, int y, int dx, int dy, int mods, int time) {
		int delta = (int) Math.round(ratio * dy * ClientSettings.WHEEL_SCALE);
		return scrollable.setVerticalScroll(lastOffset + delta);
	}

}
