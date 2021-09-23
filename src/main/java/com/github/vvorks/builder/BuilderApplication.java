package com.github.vvorks.builder;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.github.vvorks.builder.client.common.json.GwtJsonContext;
import com.github.vvorks.builder.common.json.JsonContext;
import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.util.Logger;
import com.github.vvorks.builder.server.common.logging.Slf4jLogger;

@SpringBootApplication
public class BuilderApplication {

	private static final Class<BuilderApplication> THIS = BuilderApplication.class;

    private static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		Factory.configure()
				.bindTo(Logger.class,		a -> new Slf4jLogger((Class<?>) a[0]))
				.bindTo(JsonContext.class,	a -> new GwtJsonContext(a[0]))
				;
		start(args);
	}

	public static void start(String[] args) {
		context = SpringApplication.run(THIS, args);
	}

	public static void restart() {
		ApplicationArguments args = context.getBean(ApplicationArguments.class);
		Thread thread = new Thread(() -> {
			context.close();
			context = SpringApplication.run(THIS, args.getSourceArgs());
		});
		thread.setDaemon(false);
		thread.start();
	}

	public static void stop() {
		context.close();
	}

}
