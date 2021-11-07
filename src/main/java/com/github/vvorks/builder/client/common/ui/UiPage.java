package com.github.vvorks.builder.client.common.ui;

import java.util.ArrayList;
import java.util.List;

import com.github.vvorks.builder.common.lang.Iterables;
import com.github.vvorks.builder.common.logging.Logger;

public abstract class UiPage extends UiNode {

	public static final Class<?> THIS = UiPage.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	private UiApplication application;

	protected UiPage(String name, UiApplication app) {
		super(name);
		this.application = app;
		setLeft(Length.ZERO);
		setTop(Length.ZERO);
		setRight(Length.ZERO);
		setBottom(Length.ZERO);
		initialize();
	}

	protected UiPage(UiPage src) {
		super(src);
		this.application = src.application;
	}

	protected abstract void initialize();

	@Override
	protected UiApplication getApplication() {
		return application;
	}

	@Override
	public UiPage getPage() {
		return this;
	}

	@Override
	public void onMount() {
		super.onMount();
		injectRegisteredStyles();
	}

	@Override
	public void onUnmount() {
		deinjectStyleInPage();
		super.onUnmount();
	}

	private void injectRegisteredStyles() {
		List<Iterable<UiStyle>> list = new ArrayList<>();
		list.add(this.getRegisteredStyles());
		for (UiNode d : getDescendants()) {
			list.add(d.getRegisteredStyles());
		}
		Iterable<UiStyle> flatList = Iterables.concat(list);
		StringBuilder sb = new StringBuilder();
		UiStyle.toCssStyles(flatList, sb);
		String cssString = sb.toString();
		UiApplication app = getApplication();
		DeviceContext dc = app.getDeviceContext();
		dc.injectStyleSheet(getClass(), cssString);
	}

	public void deinjectStyleInPage() {
		UiApplication app = getApplication();
		DeviceContext dc = app.getDeviceContext();
		dc.deinjectStyleSheet(getClass());
	}

}
