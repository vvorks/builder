package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.client.ClientSettings;

public class UiHorizontalScrollBar extends UiScrollBar {

	/** ドラッグ中のThumb */
	private UiThumb thumbDown;

	private int offsetDown;

	private int xDown;

	public UiHorizontalScrollBar(String name, Scrollable scrollable) {
		super(name, scrollable);
	}

	protected UiHorizontalScrollBar(UiHorizontalScrollBar src) {
		super(src);
	}

	@Override
	public UiHorizontalScrollBar copy() {
		return new UiHorizontalScrollBar(this);
	}

	@Override
	public void onVerticalScroll(UiNode node, int offset, int limit, int count) {
		//ignore vetical scroll
	}

	@Override
	public int onKeyDown(UiNode target, int keyCode, int charCode, int mods, int time) {
		int result;
		switch (keyCode) {
		case KeyCodes.LEFT:
			result = invokeHorizontalScroll(lastOffset - lastLimit) | EVENT_CONSUMED;
			break;
		case KeyCodes.RIGHT:
			result = invokeHorizontalScroll(lastOffset + lastLimit) | EVENT_CONSUMED;
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
		if (isThumbNode(target)) {
			//main又はsub thum上でのDOWN→THUMBグラブ
			thumbDown = (UiThumb) target;
			offsetDown = lastOffset;
			xDown = x;
			app.setCapture(thumbDown);
		} else if (target == this) {
			//SB上でのDOWN→上又は下へのスクロール
			Rect rect = getThumbRect();
			if (x < rect.getLeft()) {
				result = invokeHorizontalScroll(lastOffset - lastLimit);
			} else if (rect.getRight() <= x) {
				result = invokeHorizontalScroll(lastOffset + lastLimit);
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
			int delta = (int) Math.round(ratio * (x - xDown));
			result = invokeHorizontalScroll(offsetDown + delta);
		} else if (target == this) {
			//SB上でのDRAG又は押し続け→上又は下へのスクロール
			Rect rect = getThumbRect();
			if (x < rect.getLeft()) {
				result = invokeHorizontalScroll(lastOffset - lastLimit);
			} else if (rect.getRight() <= x) {
				result = invokeHorizontalScroll(lastOffset + lastLimit);
			}
		}
		return result;
	}

	@Override
	public int onMouseUp(UiNode target, int x, int y, int mods, int time) {
		int result = EVENT_IGNORED;
		if (thumbDown != null) {
			int delta = (int) Math.round(ratio * (x - xDown));
			result = invokeHorizontalScroll(offsetDown + delta);
			getApplication().releaseCapture();
		} else if (target == this) {
			//NOP
		}
		return result;
	}

	@Override
	public int onMouseWheel(UiNode target, int x, int y, int dx, int dy, int mods, int time) {
		int delta = (int) Math.round(ratio * dy * ClientSettings.WHEEL_SCALE);
		return invokeHorizontalScroll(lastOffset + delta);
	}

}
