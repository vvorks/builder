package com.github.vvorks.builder.client.common.ui;

public interface ScrollListener {
	public void onHorizontalScroll(UiNode node, int offset, int limit, int count);
	public void onVerticalScroll(UiNode node, int offset, int limit, int count);
}
