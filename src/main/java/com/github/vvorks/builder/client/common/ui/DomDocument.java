package com.github.vvorks.builder.client.common.ui;

import java.util.Map;

public interface DomDocument {

	public void injectStyleSheet(Class<?> cls, Map<String, CssStyle> cssMap);

	public void deinjectStyleSheet(Class<?> cls);

}
