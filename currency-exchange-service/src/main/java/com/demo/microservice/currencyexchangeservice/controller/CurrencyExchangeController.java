package com.demo.microservice.currencyexchangeservice.controller;

//import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.microservice.currencyexchangeservice.controller.bean.ExchangeValue;
import com.demo.microservice.currencyexchangeservice.repo.ExchangeValueRepository;

@RestController
public class CurrencyExchangeController {
	
	@Autowired
	private Environment env;
	
	@Autowired	
	private ExchangeValueRepository exchangeRepo;
	
	@GetMapping("currency-exchange/from/{from}/to/{to}")
	public ExchangeValue getExchangeValue(@PathVariable("from") String from, @PathVariable("to") String to) {
		/*new ExchangeValue(1000L, 
				from, 
				to, 
				BigDecimal.valueOf(75), 
				Integer.parseInt(env.getProperty("local.server.port")));*/
		ExchangeValue exchangeValue = exchangeRepo.findByFromAndTo(from, to);
		exchangeValue.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		return exchangeValue; 
	}
	
	// -------- Admin Area --------
	// This method should only be accessed by users with role of 'admin'
	// We'll add the logic of role based auth later
	@RequestMapping("/admin")
	public String homeAdmin() {
		return "This is the admin area of Gallery service running at port: " + env.getProperty("local.server.port");
	}
	
}
