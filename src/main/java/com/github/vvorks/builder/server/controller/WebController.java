package com.github.vvorks.builder.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebController {

	@GetMapping(path="/web/{name}")
	public String web(@PathVariable String name, Model model) {
		String str = "web" + ":" + name;
		model.addAttribute("value", str);
		return "web";
	}

}
