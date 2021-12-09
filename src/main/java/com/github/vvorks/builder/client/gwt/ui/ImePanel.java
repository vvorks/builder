package com.github.vvorks.builder.client.gwt.ui;

import java.util.HashMap;
import java.util.Map;

import com.github.vvorks.builder.client.ClientSettings;
import com.github.vvorks.builder.client.common.ui.Colors;
import com.github.vvorks.builder.client.common.ui.Length;
import com.github.vvorks.builder.client.common.ui.UiAtomicStyle;
import com.github.vvorks.builder.common.lang.Asserts;
import com.github.vvorks.builder.common.logging.Logger;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.dom.client.Text;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.FocusPanel;

public class ImePanel extends FocusPanel {

	private static final Logger LOGGER = Logger.createLogger(ImePanel.class);

	private static final Map<String, TextAlign> ALIGN_MAP = new HashMap<>();
	static {
		ALIGN_MAP.put(UiAtomicStyle.TEXT_ALIGN_LEFT, TextAlign.LEFT);
		ALIGN_MAP.put(UiAtomicStyle.TEXT_ALIGN_RIGHT, TextAlign.RIGHT);
		ALIGN_MAP.put(UiAtomicStyle.TEXT_ALIGN_CENTER, TextAlign.CENTER);
		ALIGN_MAP.put(UiAtomicStyle.TEXT_ALIGN_JUSTIFY, TextAlign.JUSTIFY);
	}

	private static final Map<String, VerticalAlign> VALIGN_MAP = new HashMap<>();
	static {
		VALIGN_MAP.put(UiAtomicStyle.VERTICAL_ALIGN_TOP, VerticalAlign.TOP);
		VALIGN_MAP.put(UiAtomicStyle.VERTICAL_ALIGN_MIDDLE, VerticalAlign.MIDDLE);
		VALIGN_MAP.put(UiAtomicStyle.VERTICAL_ALIGN_BOTTOM, VerticalAlign.BOTTOM);
	}

	private Element outer;

	private Element inner;

	private Element xAxis;

	private Element yAxis;

	public ImePanel() {
		Document doc = Document.get();
		Element elem = getElement();
		outer = doc.createDivElement();
		elem.appendChild(outer);
		outer.setTabIndex(-1);
		Style outerStyle = outer.getStyle();
		resetStyle(outerStyle);
		outerStyle.setDisplay(Display.TABLE);
		hide();
		inner = doc.createDivElement();
		inner.setTabIndex(0);
		inner.setPropertyBoolean("contentEditable", true);
		Style innerStyle = inner.getStyle();
		innerStyle.setDisplay(Display.TABLE_CELL);
		innerStyle.setOutlineWidth(0, Unit.PX);
		outer.appendChild(inner);
		addFocusHandler(new FocusHandler() {
			public void onFocus(FocusEvent event) {
				LOGGER.debug("imePanel onFocus");
				setFocus(true);
			}
		});
		addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				LOGGER.debug("imePanel onBlur");
				setFocus(false);
			}
		});
		createAxis();
	}

	private void createAxis() {
		if (ClientSettings.DEBUG) {
			Document doc = Document.get();
			Element elem = getElement();
			xAxis = doc.createDivElement();
			Style xStyle = xAxis.getStyle();
			resetStyle(xStyle);
			xStyle.setLeft(0, Unit.PX);
			xStyle.setWidth(1, Unit.PX);
			xStyle.setTop(0, Unit.PX);
			xStyle.setBottom(0, Unit.PX);
			xStyle.setBackgroundColor("cyan");
			yAxis = doc.createDivElement();
			Style yStyle = yAxis.getStyle();
			resetStyle(yStyle);
			yStyle.setLeft(0, Unit.PX);
			yStyle.setRight(0, Unit.PX);
			yStyle.setTop(0, Unit.PX);
			yStyle.setHeight(1, Unit.PX);
			yStyle.setBackgroundColor("cyan");
			elem.appendChild(xAxis);
			elem.appendChild(yAxis);
		}
	}

	private void resetStyle(Style style) {
		style.setPosition(Position.ABSOLUTE);
		style.setMargin(0, Unit.PX);
		style.setBorderWidth(0, Unit.PX);
		style.setPadding(0, Unit.PX);
		style.setOutlineWidth(0, Unit.PX);
	}

	public void setStyle(UiAtomicStyle style) {
		//GWTのStyleオブジェクト取得
		Style outerStyle = outer.getStyle();
		Style innerStyle = inner.getStyle();
		//outerの設定
		String bgColor = Colors.toCssColor(style.getBackgroundColor());
		outerStyle.setBackgroundColor(bgColor);
		String align = style.getTextAlign();
		outerStyle.setTextAlign(ALIGN_MAP.getOrDefault(align, TextAlign.LEFT));
		//innerの設定
		innerStyle.setBackgroundColor(bgColor);
		String fgColor = Colors.toCssColor(style.getColor());
		innerStyle.setColor(fgColor);
		String fontFamily = style.getFontFamily();
		Length fontSize = style.getFontSize();
		if (fontFamily != null && fontSize != null) {
			innerStyle.setProperty("fontFamily", fontFamily);
			innerStyle.setFontSize(fontSize.px(96), Unit.PX);
		}
		Length lineHeight = style.getLineHeight();
		innerStyle.setLineHeight(lineHeight.px(96), Unit.PX);
		String valign = style.getVerticalAlign();
		innerStyle.setVerticalAlign(VALIGN_MAP.getOrDefault(valign, VerticalAlign.TOP));
	}

	/**
	 * ImeAreaが現在保持している文字列を取得する
	 *
	 * @return
	 * 		ImeAreaが現在保持している文字列
	 */
	public String getText() {
		return inner.getInnerText();
	}

	/**
	 * ImeAreaの文字列を設定する
	 *
	 * @param text
	 * 		設定する文字列
	 */
	public void setText(String text) {
		inner.setInnerText(text);
	}

	/**
	 * ImeAreaの編集中文字列の選択状態を指定する
	 *
	 * @param from
	 * 		選択開始位置
	 * @param to
	 * 		選択終了位置
	 */
	public void setSelection(int from, int to) {
		//パラメータチェック
		int len = getText().length();
		Asserts.require(0 <= from && from <= len);
		Asserts.require(0 <= to   && to   <= len);
		Asserts.require(from <= to);
		//選択範囲指定
		Text t = inner.getFirstChild().cast();
		setRange(t, from, to);
	}

	/**
	 * 指定ノードの選択状態を設定する
	 *
	 * @param node
	 * 		対象ノード
	 * @param from
	 * 		開始位置
	 * @param to
	 * 		終了位置
	 */
	private static native void setRange(Text node, int from, int to)/*-{
		var range = $doc.createRange();
		range.setStart(node, from);
		range.setEnd(node, to);
		var selection = $wnd.getSelection();
		selection.removeAllRanges();
		selection.addRange(range);
	}-*/;

	/**
	 * ImeAreaを指定の表示位置に表示する
	 *
	 * @param x
	 * 		X位置（ピクセル単位、物理絶対座標）
	 * @param y
	 * 		Y位置（ピクセル単位、物理絶対座標）
	 * @param w
	 * 		幅（ピクセル単位）
	 * @param h
	 * 		高さ（ピクセル単位）
	 */
	public void show(int x, int y, int w, int h) {
		Style outerStyle = outer.getStyle();
		outerStyle.setLeft(x, Unit.PX);
		outerStyle.setTop(y, Unit.PX);
		outerStyle.setWidth(w, Unit.PX);
		outerStyle.setHeight(h, Unit.PX);
	}

	/**
	 * Imeを画面から見えなくする
	 *
	 * 注意： 非表示にするわけではなく、単に画面外に位置を移動するだけ。
	 */
	public void hide() {
		Style outerStyle = outer.getStyle();
		outerStyle.setLeft(-100, Unit.PCT);
		outerStyle.setTop(0, Unit.PX);
		outerStyle.setWidth(0, Unit.PX);
		outerStyle.setHeight(0, Unit.PX);
	}

	@Override
	public void setFocus(boolean focused) {
		if (focused) {
			inner.focus();
		} else {
			inner.blur();
		}
	}

	public void setAxis(int x, int y) {
		if (ClientSettings.DEBUG) {
			LOGGER.debug("axis %d, %d", x, y);
			xAxis.getStyle().setLeft(x, Unit.PX);
			yAxis.getStyle().setTop(y, Unit.PX);
		}
	}

}
