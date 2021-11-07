package com.github.vvorks.builder.client.common.ui;

public interface DeviceContext {

	public void injectStyleSheet(Class<?> cls, String cssString);

	public void deinjectStyleSheet(Class<?> cls);

}
