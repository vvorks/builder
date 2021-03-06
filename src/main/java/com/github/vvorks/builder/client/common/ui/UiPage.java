package com.github.vvorks.builder.client.common.ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.github.vvorks.builder.common.lang.Asserts;
import com.github.vvorks.builder.common.lang.Iterables;

public abstract class UiPage extends UiNode {

	private UiApplication application;

	private boolean initialized;

	protected UiPage(String name, UiApplication app) {
		super(name);
		Asserts.requireNotNull(app);
		this.application = app;
		setLeft(Length.ZERO);
		setTop(Length.ZERO);
		setRight(Length.ZERO);
		setBottom(Length.ZERO);
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
	protected DomDocument getDocument() {
		return application.getDocument();
	}

	@Override
	public UiPage getPage() {
		return this;
	}

	@Override
	public void onMount() {
		if (!initialized) {
			initialize();
			initialized = true;
		}
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
		Map<String, CssStyle> cssMap = new LinkedHashMap<>();
		for (UiStyle s : flatList) {
			s.toCssStyle(cssMap);
		}
		UiApplication app = getApplication();
		DomDocument doc = app.getDocument();
		doc.injectStyleSheet(getClass(), cssMap);
	}

	public void deinjectStyleInPage() {
		UiApplication app = getApplication();
		DomDocument doc = app.getDocument();
		doc.deinjectStyleSheet(getClass());
	}

}
