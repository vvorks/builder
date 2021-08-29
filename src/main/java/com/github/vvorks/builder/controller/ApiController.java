package com.github.vvorks.builder.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.vvorks.builder.BuilderApplication;
import com.github.vvorks.builder.dto.Hoge;


@RestController
public class ApiController {

	@GetMapping("/api")
	public Hoge api(@RequestParam(value = "name", defaultValue = "World") String name) {
		Hoge hoge = new Hoge();
		hoge.setBooleanValue(false);
		hoge.setDoubleValue(1.0);
		hoge.setStrValue(name);
		return hoge;
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
		return "done";
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
		return "done";
	}

}
