package com.github.vvorks.builder.client.common.util;

import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.logging.Logger;
import com.github.vvorks.builder.common.util.DelayedExecuter;
import com.google.gwt.core.client.Scheduler;

public class GwtDelayedExecuter implements DelayedExecuter {

	private static final Class<?> THIS = GwtDelayedExecuter.class;
	private static final Logger LOGGER = Factory.newInstance(Logger.class, THIS);

	@Override
	public void runAfter(int msec, Runnable cmd) {
		Scheduler.get().scheduleFixedDelay(() -> {
			try {
				cmd.run();
			} catch (RuntimeException err) {
				LOGGER.error(err);
			}
			return false;
		}, msec);
	}

	@Override
	public void runLator(Runnable cmd) {
		Scheduler.get().scheduleFinally(() -> {
			try {
				cmd.run();
			} catch (RuntimeException err) {
				LOGGER.error(err);
			}
		});
	}

}
