package com.github.vvorks.builder.client.gwt.ui;

import com.google.gwt.dom.client.Document;

public class GwtHtmlElement extends GwtDomElement {

	public GwtHtmlElement(String tag) {
		super(Document.get().createElement(tag));
	}

	@Override
	public void setAttribute(String name, String value) {
		nativeElement.setAttribute(name, value);
	}

	@Override
	public void removeAttribute(String name) {
		nativeElement.removeAttribute(name);
	}

	@Override
	public void setInnerText(String text) {
		nativeElement.setInnerText(text);
	}

	@Override
	public void setInnerHtml(String html) {
		nativeElement.setInnerHTML(html);
	}

	@Override
	public void setScrollPosition(int x, int y) {
		nativeElement.setScrollLeft(x);
		nativeElement.setScrollTop(y);
	}

}
