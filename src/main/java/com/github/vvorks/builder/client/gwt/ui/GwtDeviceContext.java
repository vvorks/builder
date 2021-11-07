package com.github.vvorks.builder.client.gwt.ui;

import java.util.LinkedHashMap;
import java.util.Map;

import com.github.vvorks.builder.client.common.ui.DeviceContext;
import com.github.vvorks.builder.common.lang.Asserts;
import com.google.gwt.dom.client.StyleElement;
import com.google.gwt.dom.client.StyleInjector;

public class GwtDeviceContext implements DeviceContext {

	/** 使用ユニット毎のスタイル要素を保持するmap */
	private Map<Class<?>, StyleElement> styles = new LinkedHashMap<>();

	@Override
	public void injectStyleSheet(Class<?> cls, String cssString) {
		Asserts.requireNotNull(cls);
		Asserts.requireNotNull(cssString);
		StyleElement style = styles.get(cls);
		if (style == null) {
			style = StyleInjector.injectStylesheet(cssString);
			styles.put(cls, style);
		} else if (!cssString.equals(style.getInnerText())) {
			StyleInjector.setContents(style, cssString);
		}
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
