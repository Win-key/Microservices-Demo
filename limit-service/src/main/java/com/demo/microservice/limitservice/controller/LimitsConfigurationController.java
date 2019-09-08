package com.demo.microservice.limitservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.microservice.limitservice.bean.LimitConfiguration;
import com.demo.microservice.limitservice.configuration.Configuration;

@RestController
public class LimitsConfigurationController {
	
	@Autowired
	private Configuration config;
	
	@GetMapping("/limits")
	public LimitConfiguration getLimits() {
		return new LimitConfiguration(config.getMinimum(), config.getMaximum());
	}

}
