package com.casestudy.security.casestudy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/casestudy")
public class HomeController {

	@RequestMapping("/home")
	public String index() {
		return "index.html";
	}
}
