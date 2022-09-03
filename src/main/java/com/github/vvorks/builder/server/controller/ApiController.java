package com.github.vvorks.builder.server.controller;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.vvorks.builder.BuilderApplication;
import com.github.vvorks.builder.server.component.PageBuilder;
import com.github.vvorks.builder.server.component.SourceWriter;
import com.github.vvorks.builder.server.component.XlsxLoader;
import com.github.vvorks.builder.server.domain.BuilderContent;
import com.github.vvorks.builder.server.domain.ClassContent;
import com.github.vvorks.builder.server.domain.EnumValueI18nContent;
import com.github.vvorks.builder.server.domain.FieldContent;
import com.github.vvorks.builder.server.domain.Subject;
import com.github.vvorks.builder.server.mapper.Mappers;
import com.github.vvorks.builder.shared.common.logging.Logger;


@RestController
public class ApiController {

	private static final Logger LOGGER = Logger.createLogger(ApiController.class);

	@Autowired
	private XlsxLoader loader;

	@Autowired
	private SourceWriter writer;

	@Autowired
	private PageBuilder builder;

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
	public String write() {
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

	@GetMapping("/makepage")
	public String makePage() {
		try {
			long t1 = System.currentTimeMillis();
			builder.process();
			long t2 = System.currentTimeMillis();
			return done("makepage", t1, t2);
		} catch (Exception err) {
			LOGGER.error(err, "ERROR");
			StringWriter w = new StringWriter();
			err.printStackTrace(new PrintWriter(w));
			return w.toString();
		}
	}

	@Autowired
	private Mappers mappers;

	@GetMapping("/classTopic")
	public List<Subject> clssTopic(@RequestParam(value = "id", defaultValue = "1") Integer id) {
		ClassContent cls = mappers.getClassMapper().get(id);
		return mappers.getClassMapper().listTopicPath(cls);
	}

	@GetMapping("/fieldTopic")
	public List<Subject> fieldTopic(@RequestParam(value = "id", defaultValue = "1") Integer id) {
		FieldContent fld = mappers.getFieldMapper().get(id);
		return mappers.getFieldMapper().listTopicPath(fld);
	}

	@GetMapping("/evI18nTopic")
	public List<Subject> evI18nTopic(@RequestParam(value = "id", defaultValue = "1") Integer id) {
		EnumValueI18nContent ev = mappers.getEnumValueI18nMapper().get(id, "KEY", "ja");
		return mappers.getEnumValueI18nMapper().listTopicPath(ev);
	}

	@GetMapping("/builderTopic")
	public List<Subject> builderTopic(@RequestParam(value = "id", defaultValue = "1") Integer id) {
		BuilderContent b = mappers.getBuilderMapper().get("Class");
		return mappers.getBuilderMapper().listTopicPath(b);
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
