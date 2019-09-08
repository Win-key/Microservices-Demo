package com.demo.microservices.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.demo.microservices.currencyconversionservice.bean.CurrencyConversionBean;
import com.demo.microservices.currencyconversionservice.proxy.CurrencyExchangeServiceProxy;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private CurrencyExchangeServiceProxy proxy;
	
	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable("from") String from, @PathVariable("to") String to, @PathVariable("quantity") BigDecimal quantity) {
		Map<String, String> uriVariables = new HashMap<String, String>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		ResponseEntity<CurrencyConversionBean> conversionResponse = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class , uriVariables);
		CurrencyConversionBean conversionBean = conversionResponse.getBody();
		System.out.println(conversionBean);
		
		return new CurrencyConversionBean(conversionBean.getId(), from, to, conversionBean.getConversionMultiple(), quantity, quantity.multiply(conversionBean.getConversionMultiple()) , conversionBean.getPort());
	}
	
	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyFeign(@PathVariable("from") String from, @PathVariable("to") String to, @PathVariable("quantity") BigDecimal quantity) {
		
		CurrencyConversionBean conversionBean = proxy.getExchangeValue(from, to); 
		System.out.println(conversionBean);
		
		return new CurrencyConversionBean(conversionBean.getId(), from, to, conversionBean.getConversionMultiple(), quantity, quantity.multiply(conversionBean.getConversionMultiple()) , conversionBean.getPort());
	}

}
