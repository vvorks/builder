package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.common.logging.Logger;

public class UiVerticalScrollBar extends UiNode implements ScrollListener {

	private static final Logger LOGGER = Logger.createLogger(UiVerticalScrollBar.class);

	private UiNode target;

	public UiVerticalScrollBar(String name, UiNode target) {
		super(name);
		this.target = target;
		this.target.addScrollListener(this);
	}

	protected UiVerticalScrollBar(UiVerticalScrollBar src) {
		super(src);
		this.target = src.target;
		this.target.addScrollListener(this);
	}

	@Override
	public UiVerticalScrollBar copy() {
		return new UiVerticalScrollBar(this);
	}

	@Override
	public void onHorizontalScroll(UiNode node, int offset, int limit, int count) {
		//NOP
	}

	@Override
	public void onVerticalScroll(UiNode node, int offset, int limit, int count) {
		LOGGER.debug("SCROLL %d %d %d", offset, limit, count);
		// TODO 自動生成されたメソッド・スタブ

	}


}
