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
		for (Map.Entry<String, Object> p : style.getProperties()) {
			String propName = p.getKey();
			String propValue = String.valueOf(p.getValue());
			if (propName.equals("background-image")) {
				propValue = "url(\"" + propValue + "\")";
				sb.append(propName).append(":").append(propValue).append(";");
				sb.append("background-size:100% 100%;");
			} else {
				sb.append(propName).append(":").append(propValue).append(";");
			}
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
