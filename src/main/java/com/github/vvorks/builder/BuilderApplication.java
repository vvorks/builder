package com.github.vvorks.builder;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BuilderApplication {

	private static final Class<BuilderApplication> THIS = BuilderApplication.class;

    private static ConfigurableApplicationContext context;

	public static void main(String[] args) {
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
