package com.github.vvorks.builder.client.gwt.ui;

import java.util.LinkedHashMap;
import java.util.Map;

import com.github.vvorks.builder.client.common.ui.CssStyle;
import com.github.vvorks.builder.client.common.ui.DomDocument;
import com.github.vvorks.builder.client.common.ui.DomElement;
import com.github.vvorks.builder.client.common.ui.UiNode;
import com.github.vvorks.builder.common.lang.Asserts;
import com.google.gwt.dom.client.StyleElement;
import com.google.gwt.dom.client.StyleInjector;

public class GwtDomDocument implements DomDocument {

	private static final String[] BOUNDS_KEYS = {
		"left", "right", "width", "top", "bottom", "height"
	};

	private static final int LEFT_BIT	= 0x0001;
	private static final int RIGHT_BIT	= 0x0002;
	private static final int WIDTH_BIT	= 0x0004;
	private static final int TOP_BIT	= 0x0008;
	private static final int BOTTOM_BIT	= 0x0010;
	private static final int HEIGHT_BIT	= 0x0020;
	private static final int HORZ_BITS	= LEFT_BIT|RIGHT_BIT|WIDTH_BIT;
	private static final int VERT_BITS	= TOP_BIT|BOTTOM_BIT|HEIGHT_BIT;

	private static int getBoundsBit(String key) {
		for (int i = 0; i < BOUNDS_KEYS.length; i++) {
			if (BOUNDS_KEYS[i].equals(key)) {
				return 1 << i;
			}
		}
		return 0;
	}

	/** 使用ユニット毎のスタイル要素を保持するmap */
	private Map<Class<?>, StyleElement> styles = new LinkedHashMap<>();

	@Override
	public DomElement createElement(String ns, String tag, UiNode owner) {
		if (isCanvas(ns, tag)) {
			return new GwtCanvasElement(this, tag);
		} else {
			return new GwtDomElement(this, tag);
		}
	}

	private boolean isCanvas(String ns, String tag) {
		return	UiNode.NS_CANVAS.equals(ns) ||
				((ns == null || UiNode.NS_HTML.equals(ns)) && UiNode.HTML_CANVAS.equals(tag));
	}

	@Override
	public void injectStyleSheet(Class<?> cls, Map<String, CssStyle> cssMap) {
		Asserts.requireNotNull(cls);
		Asserts.requireNotNull(cssMap);
		String cssString = toCssString(cssMap);
		StyleElement style = styles.get(cls);
		if (style == null) {
			style = StyleInjector.injectStylesheet(cssString);
			styles.put(cls, style);
		} else if (!cssString.equals(style.getInnerText())) {
			StyleInjector.setContents(style, cssString);
		}
	}

	private String toCssString(Map<String, CssStyle> cssMap) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, CssStyle> e : cssMap.entrySet()) {
			sb.append(e.getKey()).append(" {");
			sb.append(toCssString(e.getValue()));
			sb.append("}\n");
		}
		return sb.toString();
	}

	public String toCssString(CssStyle style) {
		StringBuilder sb = new StringBuilder();
		int boundsBits = 0;
		for (Map.Entry<String, Object> p : style.getProperties()) {
			String propName = p.getKey();
			Object propValue = p.getValue();
			String propStr = String.valueOf(propValue);
			boundsBits |= getBoundsBit(propStr);
			if (propName.equals("background-image")) {
				propStr = "url(\"" + propStr + "\")";
				sb.append(propName).append(":").append(propStr).append(";");
				sb.append("background-size:100% 100%;");
			} else {
				sb.append(propName).append(":").append(propStr).append(";");
			}
		}
		if ((boundsBits & HORZ_BITS) == WIDTH_BIT && (boundsBits & VERT_BITS) == HEIGHT_BIT) {
			sb.append("left:50%;top:50%;transform:translate(-50%,-50%)");
		} else if ((boundsBits & HORZ_BITS) == WIDTH_BIT ) {
			sb.append("left:50%;transform:translateX(-50%)");
		} else if ((boundsBits & VERT_BITS) == HEIGHT_BIT) {
			sb.append("top:50%;transform:translateY(-50%)");
		}
		return sb.toString();
	}

	@Override
	public void deinjectStyleSheet(Class<?> cls) {
		Asserts.requireNotNull(cls);
		StyleElement style = styles.get(cls);
		if (style != null) {
			styles.remove(cls);
			style.removeFromParent();
		}
	}

}
