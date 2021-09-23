package com.github.vvorks.builder.common.util;

public interface DelayedExecuter {
	public void runAfter(int msec, Runnable cmd);
	public void runLator(Runnable cmd);
}
