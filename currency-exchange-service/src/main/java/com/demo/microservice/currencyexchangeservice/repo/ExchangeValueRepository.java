package com.demo.microservice.currencyexchangeservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.microservice.currencyexchangeservice.controller.bean.ExchangeValue;

public interface ExchangeValueRepository extends JpaRepository<ExchangeValue, Long>{
	
	ExchangeValue findByFromAndTo(String from, String to);
	
}
