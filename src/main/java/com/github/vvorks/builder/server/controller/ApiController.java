package com.github.vvorks.builder.server.controller;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

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
			loader.process(new File(name + ".xlsx"));
			return done();
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
			writer.process();
			return done();
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
		return done();
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
		return done();
	}

	private String done() {
		return String.format("done at %tY/%<tm/%<td %<tH:%<tM:%<tS", new Date());
	}

}
