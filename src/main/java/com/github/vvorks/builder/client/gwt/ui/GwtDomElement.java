package com.github.vvorks.builder.client.gwt.ui;

import com.github.vvorks.builder.client.common.ui.DomElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;

public class GwtDomElement implements DomElement {

	private Element nativeElement;

	public GwtDomElement(Object tag) {
		this.nativeElement = Document.get().createElement(tag.toString());
	}

	public Element getNativeElement() {
		return nativeElement;
	}

	@Override
	public void setParent(DomElement newParent) {
		Element np = newParent != null ? ((GwtDomElement) newParent).getNativeElement() : null;
		Element me = this.getNativeElement();
		Element op = me.getParentElement();
		if (np != op) {
			if (op != null) {
				op.removeChild(me);
			}
			if (np != null) {
				np.appendChild(me);
			}
		}
	}

	@Override
	public DomElement appendChild(DomElement newChild) {
		GwtDomElement e = (GwtDomElement) newChild;
		this.nativeElement.appendChild(e.getNativeElement());
		return newChild;
	}

	@Override
	public DomElement removeChild(DomElement oldChild) {
		GwtDomElement e = (GwtDomElement) oldChild;
		this.nativeElement.removeChild(e.getNativeElement());
		return oldChild;
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
