package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.common.logging.Logger;

public class UiList extends UiNode {

	public static final Class<?> THIS = UiList.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	public UiList() {
		super();
	}

	public UiList(String name) {
		super(name);
	}

	public UiList(UiList src) {
		super(src);
	}

	@Override
	public UiList copy() {
		return new UiList(this);
	}

}
