package com.github.vvorks.builder.server.controller;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.vvorks.builder.BuilderApplication;
import com.github.vvorks.builder.common.lang.Factory;
import com.github.vvorks.builder.common.util.Logger;
import com.github.vvorks.builder.server.component.SourceWriter;
import com.github.vvorks.builder.server.component.XlsxLoader;


@RestController
public class ApiController {

	private static final Class<?> THIS = XlsxLoader.class;
	private static final Logger LOGGER = Factory.newInstance(Logger.class, THIS);

	@Autowired
	private XlsxLoader loader;

	@Autowired
	private SourceWriter writer;

	@GetMapping("/load")
	public String load(@RequestParam(value = "name", defaultValue = "input") String name) {
		try {
			long t1 = System.currentTimeMillis();
			loader.process(new File(name + ".xlsx"));
			long t2 = System.currentTimeMillis();
			return done("load", t1, t2);
		} catch (Exception err) {
			LOGGER.error(err, "ERROR");
			StringWriter w = new StringWriter();
			err.printStackTrace(new PrintWriter(w));
			return w.toString();
		}
	}

	@GetMapping("/write")
	public String write(@RequestParam(value = "name", defaultValue = "input") String name) {
		try {
			long t1 = System.currentTimeMillis();
			writer.process();
			long t2 = System.currentTimeMillis();
			return done("write", t1, t2);
		} catch (Exception err) {
			LOGGER.error(err, "ERROR");
			StringWriter w = new StringWriter();
			err.printStackTrace(new PrintWriter(w));
			return w.toString();
		}
	}

	/**
	 * システム再起動
	 *
	 * TODO 危険なAPIなので何らかの制限処理が必要
	 *
	 * @return 応答文字列
	 */
	@GetMapping(path="/system/restart")
	public String restart() {
		BuilderApplication.restart();
		return "restart ok";
	}

	/**
	 * システム停止
	 *
	 * TODO 危険なAPIなので何らかの制限処理が必要
	 *
	 * @return 応答文字列
	 */
	@GetMapping(path="/system/stop")
	public String stop() {
		BuilderApplication.stop();
		return "stop ok";
	}

	private String done(String name, long t1, long t2) {
		return String.format("%s: %tY/%<tm/%<td %<tH:%<tM:%<tS - %tY/%<tm/%<td %<tH:%<tM:%<tS (%d msec)",
				name,
				t1,
				t2,
				t2 - t1);
	}

}
