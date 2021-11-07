package com.github.vvorks.builder.client.gwt.ui;

import java.util.Map;

import com.github.vvorks.builder.client.common.ui.KeyCodes;
import com.github.vvorks.builder.client.common.ui.UiApplication;
import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.logging.Logger;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FocusWidget;

public class DomPanel extends FocusWidget {

	public static final Class<?> THIS = DomPanel.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	protected final UiApplication app;

	public DomPanel() {
		//アプリケーションの作成
		app = Factory.newInstance(UiApplication.class);
		//イベントハンドラの初期化
		addKeyDownHandler(event -> onKeyDown(event));
		addKeyPressHandler(event -> onKeyPress(event));
		addKeyUpHandler(event -> onKeyUp(event));
		addMouseDownHandler(event -> onMouseDown(event));
		addMouseMoveHandler(event -> onMouseMove(event));
		addMouseUpHandler(event -> onMouseUp(event));
		addClickHandler(event -> onClick(event));
		addDoubleClickHandler(event -> onDoubleClick(event));
		addMouseWheelHandler(event -> onMouseWheel(event));
		Window.addResizeHandler(event -> onResize(event));
	}

	private void onKeyDown(KeyDownEvent event) {
		int keyCode = event.getNativeKeyCode();
		int mods = getModifier(event) & ~KeyCodes.MOD_BUTTON;
		int timestamp = getTimeStamp(event);
		int result = app.processKeyDown(keyCode, 0, mods, timestamp);
		if ((result & UiApplication.EVENT_CONSUMED) != 0) {
			event.preventDefault();
		}
	}

	private void onKeyPress(KeyPressEvent event) {
		int charCode = event.getCharCode();
		int mods = getModifier(event) & ~KeyCodes.MOD_BUTTON;
		int timestamp = getTimeStamp(event);
		int result = app.processKeyPress(0, charCode, mods, timestamp);
		if ((result & UiApplication.EVENT_CONSUMED) != 0) {
			event.preventDefault();
		}
	}

	private void onKeyUp(KeyUpEvent event) {
		int keyCode = event.getNativeKeyCode();
		int mods = getModifier(event) & ~KeyCodes.MOD_BUTTON;
		int timestamp = getTimeStamp(event);
		int result = app.processKeyUp(keyCode, 0, mods, timestamp);
		if ((result & UiApplication.EVENT_CONSUMED) != 0) {
			event.preventDefault();
		}
	}

	private void onMouseDown(MouseDownEvent event) {
		int x = event.getClientX();
		int y = event.getClientY();
		int mods = getModifier(event);
		int timestamp = getTimeStamp(event);
		int result = app.processMouseDown(x, y, mods, timestamp);
		if ((result & UiApplication.EVENT_CONSUMED) != 0) {
			event.preventDefault();
		}
	}

	private void onMouseMove(MouseMoveEvent event) {
		int x = event.getClientX();
		int y = event.getClientY();
		int mods = getModifier(event);
		int timestamp = getTimeStamp(event);
		int result = app.processMouseMove(x, y, mods, timestamp);
		if ((result & UiApplication.EVENT_CONSUMED) != 0) {
			event.preventDefault();
		}
	}

	private void onMouseUp(MouseUpEvent event) {
		int x = event.getClientX();
		int y = event.getClientY();
		int mods = getModifier(event);
		int timestamp = getTimeStamp(event);
		int result = app.processMouseUp(x, y, mods, timestamp);
		if ((result & UiApplication.EVENT_CONSUMED) != 0) {
			event.preventDefault();
		}
	}

	private void onClick(ClickEvent event) {
		int x = event.getClientX();
		int y = event.getClientY();
		int mods = getModifier(event);
		int timestamp = getTimeStamp(event);
		int result = app.processMouseClick(x, y, mods, timestamp);
		if ((result & UiApplication.EVENT_CONSUMED) != 0) {
			event.preventDefault();
		}
	}

	private void onDoubleClick(DoubleClickEvent event) {
		int x = event.getClientX();
		int y = event.getClientY();
		int mods = getModifier(event) | KeyCodes.MOD_DOUBLECLICK;
		int timestamp = getTimeStamp(event);
		int result = app.processMouseClick(x, y, mods, timestamp);
		if ((result & UiApplication.EVENT_CONSUMED) != 0) {
			event.preventDefault();
		}
	}

	private void onMouseWheel(MouseWheelEvent event) {
		int x = event.getClientX();
		int y = event.getClientY();
		int deltaX = 0;
		int deltaY = event.getDeltaY();
		int mods = getModifier(event);
		int timestamp = getTimeStamp(event);
		int result = app.processMouseWheel(x, y, deltaX, deltaY, mods, timestamp);
		if ((result & UiApplication.EVENT_CONSUMED) != 0) {
			event.preventDefault();
		}
	}

	private void onResize(ResizeEvent event) {
		int screenWidth = event.getWidth();
		int screenHeight = event.getHeight();
		int timestamp = newTimestamp();
		app.processResize(screenWidth, screenHeight, timestamp);
	}

	private int getTimeStamp(DomEvent<?> event) {
		return getTimeStamp(event.getNativeEvent());
	}

	private static native int getTimeStamp(NativeEvent event)/*-{
		return Math.round(event.timeStamp);
	}-*/;

	private static native int newTimestamp()/*-{
		var event = $doc.createEvent('Event');
		event.initEvent('resize', true, true);
		return Math.round(event.timeStamp);
	}-*/;

	private int getModifier(DomEvent<?> event) {
		NativeEvent e = event.getNativeEvent();
		int mod = 0;
		//Browserイベント状態からApplicationのキーイベント修飾子を算出
		if (e.getShiftKey()) {
			mod |= KeyCodes.MOD_SHIFT;
		}
		if (e.getCtrlKey()) {
			mod |= KeyCodes.MOD_CTRL;
		}
		if (e.getAltKey()) {
			mod |= KeyCodes.MOD_ALT;
		}
		if (e.getMetaKey()) {
			mod |= KeyCodes.MOD_META;
		}
		//Browserイベント状態からApplicationのボタンイベント修飾子を算出
		switch (e.getButton()) {
		case NativeEvent.BUTTON_LEFT:
			mod |= KeyCodes.MOD_LBUTTON;
			break;
		case NativeEvent.BUTTON_MIDDLE:
			mod |= KeyCodes.MOD_MBUTTON;
			break;
		case NativeEvent.BUTTON_RIGHT:
			mod |= KeyCodes.MOD_RBUTTON;
			break;
		default:
			break;
		}
		return mod;
	}

	public void load(String tag, Map<String, String> parameters) {
		int screenWidth = Window.getClientWidth();
		int screenHeight = Window.getClientHeight();
		app.processInitialize(screenWidth, screenHeight);
		app.processLoad(tag, parameters);
	}

}
