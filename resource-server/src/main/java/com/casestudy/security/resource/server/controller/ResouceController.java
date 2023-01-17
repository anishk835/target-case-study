package com.casestudy.security.resource.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResouceController {

	@GetMapping("api/resources")
	public String hello() {
		return "Hello, welcome to resouces!";
	}
}
