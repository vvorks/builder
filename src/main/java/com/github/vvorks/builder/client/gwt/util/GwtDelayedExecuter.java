package com.github.vvorks.builder.client.gwt.util;

import com.github.vvorks.builder.shared.common.logging.Logger;
import com.github.vvorks.builder.shared.common.util.DelayedExecuter;
import com.google.gwt.core.client.Scheduler;

public class GwtDelayedExecuter implements DelayedExecuter {

	private static final Logger LOGGER = Logger.createLogger(GwtDelayedExecuter.class);

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
