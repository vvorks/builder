package com.github.vvorks.builder.client.common.ui;

public interface DomElement {

	public void setParent(DomElement newParent);

	public void setDefinedStyle(UiAtomicStyle style);

	public void setLocalStyle(CssStyle style);

	public void setBounds(int left, int top, int width, int height);

	public void setInnerText(String text);

	public void setScrollBounds(int x, int y, int width, int height);

	public void sync();

}
