package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.common.lang.Factory;

public class UiCanvasFrame extends UiNode {

	/** キャンバスElement */
	private DomElement canvasElement;

	public UiCanvasFrame(String name) {
		super(name);
	}

	protected UiCanvasFrame(UiCanvasFrame src) {
		super(src);
	}

	@Override
	public UiCanvasFrame copy() {
		return new UiCanvasFrame(this);
	}

	@Override
	protected DomElement createDomElement(String namespaceURI, String qualifiedName, UiNode owner) {
		if (owner != this) {
			return Factory.newInstance(DomElement.class, NS_CANVAS, qualifiedName, owner);
		}
		return super.createDomElement(namespaceURI, qualifiedName, owner);
	}

	@Override
	protected void setDomElement(DomElement element) {
		super.setDomElement(element);
		canvasElement = createDomElement(NS_HTML, HTML_CANVAS, this);
		canvasElement.setParent(element);
	}

	@Override
	protected void syncStyle() {
		super.syncStyle();
		syncInnerStyle();
	}

	private void syncInnerStyle() {
		CssStyle.Builder sb = new CssStyle.Builder()
				.property("position", "absolute")
				.property("overflow", "hidden")
				.left(Length.ZERO)
				.top(Length.ZERO)
				.width(Length.FULL)
				.height(Length.FULL);
		canvasElement.setAttribute("style", sb.build().toString());
	}

	@Override
	protected void syncDomElement(DomElement domElement) {
		super.syncDomElement(canvasElement);
	}

}
