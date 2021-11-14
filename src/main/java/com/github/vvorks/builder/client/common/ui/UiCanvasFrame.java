package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.common.lang.Factory;

public class UiCanvasFrame extends UiNode {

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
		return super.createDomElement(NS_HTML, HTML_CANVAS, owner);
	}

}
