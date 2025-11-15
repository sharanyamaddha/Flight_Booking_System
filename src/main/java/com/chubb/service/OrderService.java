package com.chubb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.chubb.repository.OrderRepository;
import com.chubb.request.Order1;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService { //to implement businessRules/Logic
	@Autowired
	OrderRepository orderRepository;
	
	// AddressRepository addressRepository;
	public void insertOrder(Order1 order) {
		
		System.out.println(order);
		
		log.debug(order.toString());
		orderRepository.save(order);
		
		
	}

}
