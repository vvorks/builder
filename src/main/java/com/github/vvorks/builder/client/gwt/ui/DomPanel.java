package com.github.vvorks.builder.client.gwt.ui;

import java.util.Map;

import com.github.vvorks.builder.client.common.ui.DomDocument;
import com.github.vvorks.builder.client.common.ui.KeyCodes;
import com.github.vvorks.builder.client.common.ui.Rect;
import com.github.vvorks.builder.client.common.ui.UiApplication;
import com.github.vvorks.builder.client.common.ui.UiAtomicStyle;
import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.logging.Logger;
import com.google.gwt.dom.client.Element;
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
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Panel;

public class DomPanel extends FocusPanel {

	private static final Logger LOGGER = Logger.createLogger(DomPanel.class);

	/** getModifier()の動作パラメータ：キー修飾子を取得する */
	private static final int GM_KEY     = 1;

	/** getModifier()の動作パラメータ：マウス修飾子を取得する */
	private static final int GM_MOUSE   = 2;

	/** getModifier()の動作パラメータ：マウスUP修飾子を取得する */
	private static final int GM_MOUSEUP = 4;

	/** バックヤードパネル（画像取得・保持用パネル） */
	protected final Panel backyard;

	/** IMEパネル（文字入力用） */
	protected final ImePanel imePanel;

	/** UIアプリケーション */
	protected final UiApplication app;

	/**
	 * DOMパネルを作成する
	 *
	 * @param backyard バックヤードパネル
	 * @param imePanel IMEパネル
	 */
	public DomPanel(Panel backyard, ImePanel imePanel) {
		//sub panels初期化
		this.backyard = backyard;
		this.imePanel = imePanel;
		//アプリケーションの作成
		DomDocument doc = new GwtDomDocument(this);
		app = Factory.newInstance(UiApplication.class, doc);
		//このパネルのElementを取得
		Element panelElement = getElement();
		//ルートノードの作成とこのパネルのElementとの関連付け
		Element e = ((GwtDomElement) app.getRootElement()).getNativeElement();
		panelElement.appendChild(e);
	}

	/**
	 * バックヤードパネルを取得する
	 *
	 * @return バックヤードパネル
	 */
	public Panel getBackyard() {
		return backyard;
	}

	public void onKeyDown(KeyDownEvent event) {
		int keyCode = event.getNativeKeyCode();
		int mods = getModifier(event, GM_KEY);
		int timestamp = getTimeStamp(event);
		int result = app.processKeyDown(keyCode, 0, mods, timestamp);
		if ((result & UiApplication.EVENT_THROUGH) == UiApplication.EVENT_CONSUMED) {
			event.preventDefault();
		}
	}

	private static native void flush(Element element)/*-{
  		var disp = element.style.display;
		element.style.display = 'none';
		var trick = element.offsetHeight;
  		element.style.display = disp;
	}-*/;

	public void onKeyPress(KeyPressEvent event) {
		int charCode = event.getCharCode();
		int mods = getModifier(event, GM_KEY);
		int timestamp = getTimeStamp(event);
		int result = app.processKeyPress(0, charCode, mods, timestamp);
		if ((result & UiApplication.EVENT_THROUGH) == UiApplication.EVENT_CONSUMED) {
			event.preventDefault();
		}
	}

	public void onKeyUp(KeyUpEvent event) {
		int keyCode = event.getNativeKeyCode();
		int mods = getModifier(event, GM_KEY);
		int timestamp = getTimeStamp(event);
		int result = app.processKeyUp(keyCode, 0, mods, timestamp);
		if ((result & UiApplication.EVENT_THROUGH) == UiApplication.EVENT_CONSUMED) {
			event.preventDefault();
		}
	}

	public void onInput(InputEvent event) {
		String data = event.getData();
		String content = event.getContent();
		int mods = getModifier(event, GM_KEY);
		int timestamp = getTimeStamp(event);
		int result = app.processInput(data, content, mods, timestamp);
		if ((result & UiApplication.EVENT_THROUGH) == UiApplication.EVENT_CONSUMED) {
			event.preventDefault();
		}
	}

	public void onMouseDown(MouseDownEvent event) {
		int x = event.getClientX();
		int y = event.getClientY();
		int mods = getModifier(event, GM_KEY|GM_MOUSE);
		int timestamp = getTimeStamp(event);
		int result = app.processMouseDown(x, y, mods, timestamp);
		if ((result & UiApplication.EVENT_THROUGH) == UiApplication.EVENT_CONSUMED) {
			event.preventDefault();
		}
	}

	public void onMouseMove(MouseMoveEvent event) {
		int x = event.getClientX();
		int y = event.getClientY();
		int mods = getModifier(event, GM_KEY|GM_MOUSE);
		int timestamp = getTimeStamp(event);
		int result = app.processMouseMove(x, y, mods, timestamp);
		if ((result & UiApplication.EVENT_THROUGH) == UiApplication.EVENT_CONSUMED) {
			event.preventDefault();
		}
	}

	public void onMouseUp(MouseUpEvent event) {
		int x = event.getClientX();
		int y = event.getClientY();
		int mods = getModifier(event, GM_KEY|GM_MOUSEUP);
		int timestamp = getTimeStamp(event);
		int result = app.processMouseUp(x, y, mods, timestamp);
		if ((result & UiApplication.EVENT_THROUGH) == UiApplication.EVENT_CONSUMED) {
			event.preventDefault();
		}
	}

	public void onClick(ClickEvent event) {
		int x = event.getClientX();
		int y = event.getClientY();
		int mods = getModifier(event, GM_KEY|GM_MOUSEUP);
		int timestamp = getTimeStamp(event);
		int result = app.processMouseClick(x, y, mods, timestamp);
		if ((result & UiApplication.EVENT_THROUGH) == UiApplication.EVENT_CONSUMED) {
			event.preventDefault();
		}
	}

	public void onDoubleClick(DoubleClickEvent event) {
		int x = event.getClientX();
		int y = event.getClientY();
		int mods = getModifier(event, GM_KEY|GM_MOUSEUP) | KeyCodes.MOD_DOUBLECLICK;
		int timestamp = getTimeStamp(event);
		int result = app.processMouseClick(x, y, mods, timestamp);
		if ((result & UiApplication.EVENT_THROUGH) == UiApplication.EVENT_CONSUMED) {
			event.preventDefault();
		}
	}

	public void onMouseWheel(MouseWheelEvent event) {
		int x = event.getClientX();
		int y = event.getClientY();
		int deltaX = 0;
		int deltaY = event.getDeltaY();
		int mods = getModifier(event, GM_KEY|GM_MOUSE);
		int timestamp = getTimeStamp(event);
		int result = app.processMouseWheel(x, y, deltaX, deltaY, mods, timestamp);
		if ((result & UiApplication.EVENT_THROUGH) == UiApplication.EVENT_CONSUMED) {
			event.preventDefault();
		}
	}

	public void onResize(ResizeEvent event) {
		int screenWidth = event.getWidth();
		int screenHeight = event.getHeight();
		int timestamp = newTimestamp();
		app.processResize(screenWidth, screenHeight, timestamp);
	}

	public void onAnimationFrame() {
		int timestamp = newTimestamp();
		app.processAnimationFrame(timestamp);
	}

	public void onImageLoaded(String url) {
		int timestamp = newTimestamp();
		app.processImageLoaded(url, timestamp);
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

	/**
	 * イベントからキー／マウスの修飾子を取得する
	 *
	 * @param event 発生イベント
	 * @param flags GM_*の組み合わせ
	 * @return 修飾子のビット列（KeyCodes.MOD_*）
	 */
	private int getModifier(DomEvent<?> event, int flags) {
		NativeEvent e = event.getNativeEvent();
		int mod = 0;
		if ((flags & GM_KEY) != 0) {
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
		}
		if ((flags & GM_MOUSE) != 0) {
			mod |= (getButtons(e) & KeyCodes.MOD_BUTTONS);
		} else if ((flags & GM_MOUSEUP) != 0) {
			mod |= (e.getButton() & KeyCodes.MOD_BUTTONS);
		}
		return mod;
	}

	private static native int getButtons(NativeEvent e)/*-{
		return e.buttons | 0;
	}-*/;

	public void load(String tag, Map<String, String> parameters) {
		int screenWidth = Window.getClientWidth();
		int screenHeight = Window.getClientHeight();
		app.processInitialize(screenWidth, screenHeight);
		app.processLoad(tag, parameters);
	}

	public void startEditing(Rect r, UiAtomicStyle style, String text, boolean fireInputEvent) {
		imePanel.setStyle(style);
		imePanel.setText(text);
		int len = text.length();
		if (len > 0) {
			imePanel.setSelection(len, len);
		}
		imePanel.show(r.getLeft(), r.getTop(), r.getWidth(), r.getHeight());
		if (fireInputEvent) {
			app.processInput("", text, 0, newTimestamp());
		}
	}

	public String endEditing() {
		String text = imePanel.getText();
		imePanel.setText("");
		imePanel.hide();
		return text;
	}

	public void setAxis(int x, int y) {
		imePanel.setAxis(x, y);
	}

}
