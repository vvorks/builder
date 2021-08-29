package com.github.vvorks.builder.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
