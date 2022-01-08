package com.github.vvorks.builder.client.common.ui;

public interface Scrollable {

	public interface Listener {
		public void onHorizontalScroll(UiNode node, int offset, int limit, int count);
		public void onVerticalScroll(UiNode node, int offset, int limit, int count);
	}

	public void addScrollableListener(Scrollable.Listener listener);
	public void removeScrollableListener(Scrollable.Listener listener);

	public int setHorizontalScroll(int offset);
	public int setVerticalScroll(int offset);

}
