package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.logging.Logger;

public class UiRoot extends UiNode {

	public static final Logger LOGGER = Logger.createLogger(UiRoot.class);

	private DomDocument document;

	public UiRoot(DomDocument document) {
		this.document = document;
	}

	protected UiRoot(UiRoot src) {
		super(src);
	}

	@Override
	public UiRoot copy() {
		return new UiRoot(this);
	}

	@Override
	protected DomDocument getDocument() {
		return document;
	}

	@Override
	public DomElement getDomElement() {
		ensureDomElement();
		return super.getDomElement();
	}

	@Override
	protected int getParentWidthPx() {
		return Factory.getInstance(Metrics.class).getScreenWidth();
	}

	@Override
	protected int getParentHeightPx() {
		return Factory.getInstance(Metrics.class).getScreenHeight();
	}

	@Override
	public void onResize(int screenWidth, int screenHeight) {
		setBounds(Length.ZERO, Length.ZERO, null, null, new Length(screenWidth), new Length(screenHeight));
		super.onResize(screenWidth, screenHeight);
	}

}
