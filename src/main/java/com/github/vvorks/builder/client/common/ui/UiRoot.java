package com.github.vvorks.builder.client.common.ui;

public class UiRoot extends UiNode {

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
	public String getName() {
		return "";
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
		return Metrics.get().getScreenWidth();
	}

	@Override
	protected int getParentHeightPx() {
		return Metrics.get().getScreenHeight();
	}

	@Override
	public void onResize(int screenWidth, int screenHeight) {
		setBounds(Length.ZERO, Length.ZERO, null, null, new Length(screenWidth), new Length(screenHeight));
		super.onResize(screenWidth, screenHeight);
	}

}
