package com.github.vvorks.builder.client.common.ui;

public interface DomElement {

	public void setParent(DomElement newParent);

	public void setAttribute(String name, String value);

	public void removeAttribute(String name);

	public void setInnerText(String text);

	public void setInnerHtml(String html);

	public void setScrollPosition(int x, int y);

}
