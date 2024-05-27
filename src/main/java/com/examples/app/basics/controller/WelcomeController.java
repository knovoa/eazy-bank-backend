package com.examples.app.basics.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class WelcomeController {

	@GetMapping("/")
	public String sayHello() {
		log.info("passing welcome controller >> sayHello() method");
		return "Welcome to Spring Application with basic security";
	}
}
