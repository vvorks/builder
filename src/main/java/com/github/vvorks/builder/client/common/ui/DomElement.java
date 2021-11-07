package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.common.lang.Factory;

public interface DomElement {

	public static DomElement createDomElement(String tag) {
		return Factory.newInstance(DomElement.class, tag);
	}

	public void setParent(DomElement newParent);

	public DomElement appendChild(DomElement newChild);

	public DomElement removeChild(DomElement oldChild);

	public void setAttribute(String name, String value);

	public void removeAttribute(String name);

	public void setInnerText(String text);

	public void setInnerHtml(String html);

	public void setScrollPosition(int x, int y);

}
