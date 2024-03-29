package com.github.vvorks.builder.shared.common.util;

import com.github.vvorks.builder.shared.common.lang.Factory;

public interface DelayedExecuter {
	public static DelayedExecuter get() {
		return Factory.getInstance(DelayedExecuter.class);
	}
	public void runAfter(int msec, Runnable cmd);
	public void runLator(Runnable cmd);
}
