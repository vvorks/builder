package com.github.vvorks.builder.client.common.ui;

import com.github.vvorks.builder.common.logging.Logger;

public class UiHorizontalGroup extends UiGroup {

	public static final Class<?> THIS = UiHorizontalGroup.class;
	public static final Logger LOGGER = Logger.createLogger(THIS);

	public UiHorizontalGroup(String name) {
		super(name);
	}

	protected UiHorizontalGroup(UiGroup src) {
		super(src);
	}

	@Override
	public UiHorizontalGroup copy() {
		return new UiHorizontalGroup(this);
	}

}
