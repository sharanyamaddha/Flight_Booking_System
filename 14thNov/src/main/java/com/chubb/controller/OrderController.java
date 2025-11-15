package com.chubb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chubb.request.Order1;
import com.chubb.service.OrderService;

import jakarta.validation.Valid;

@RestController
public class OrderController {  //receive http request
	@Autowired
	OrderService service;
	@GetMapping("/order")
	String getOrder() {
		return "hello";
	}
	
		
	@PostMapping("/order")
	Order1 saveOrder(@Valid @RequestBody Order1 order) {
		service.insertOrder(order);
		return order;	
		}

	@PostMapping("/orderprice")
	float Totalprice(@RequestBody Order1 order) {
		return order.getPrice()*order.getQuantity();
	}
	
	


}
