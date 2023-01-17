package com.casestudy.client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/api/hello")
	public String helloApi() {
		return "Hello, welcome from the client";
	}
	
	@GetMapping("/hello")
	public String hello() {
		return "Hello, welcome from the client without api request";
	}
}
