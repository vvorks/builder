package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.common.logging.Logger;

/** テスト用ノード */
public class UiBox extends UiNode {

	public static final Class<?> THIS = UiBox.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	public UiBox() {
		super();
	}

	public UiBox(UiBox src) {
		super(src);
	}

	@Override
	public UiBox copy() {
		return new UiBox(this);
	}

	@Override
	public int onKeyDown(UiNode target, int keyCode, int charCode, int mods, int time) {
		return EVENT_EATEN;
	}

	@Override
	public int onKeyPress(UiNode target, int keyCode, int charCode, int mods, int time) {
		return EVENT_EATEN;
	}

	@Override
	public int onKeyUp(UiNode target, int keyCode, int charCode, int mods, int time) {
		return EVENT_EATEN;
	}

	@Override
	public int onMouseMove(UiNode target, int x, int y, int mods, int time) {
		return EVENT_EATEN;
	}

	@Override
	public int onMouseDown(UiNode target, int x, int y, int mods, int time) {
		return EVENT_EATEN;
	}

	@Override
	public int onMouseUp(UiNode target, int x, int y, int mods, int time) {
		return EVENT_EATEN;
	}

	@Override
	public int onMouseClick(UiNode target, int x, int y, int mods, int time) {
		return EVENT_EATEN;
	}

	@Override
	public int onMouseWheel(UiNode target, int x, int y, int dx, int dy, int mods, int time) {
		return EVENT_EATEN;
	}

}
