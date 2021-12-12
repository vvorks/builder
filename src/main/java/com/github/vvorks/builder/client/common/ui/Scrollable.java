package com.github.vvorks.builder.client.common.ui;

public interface Scrollable {

	public default void addScrollListener(ScrollListener listener) {}
	public default void removeScrollListener(ScrollListener listener) {}
	public int setHorizontalScroll(int offset);
	public int setVerticalScroll(int offset);

}
