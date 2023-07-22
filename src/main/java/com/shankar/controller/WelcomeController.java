package com.shankar.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
	
	@GetMapping("/wel")
	public String welcom()
	{
		return "wecome to Ashok It !!..";
	}

}
