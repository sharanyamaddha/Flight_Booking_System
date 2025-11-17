package com.flightapp.controller;

import org.springframework.http.HttpStatus;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.dto.request.FlightRequest;
import com.flightapp.dto.response.FlightResponse;
import com.flightapp.interfaces.FlightService;

import jakarta.validation.Valid;

@RestController
public class FlightController {

	FlightService flightService;
	
	 public FlightController(FlightService flightService) {
	        this.flightService = flightService;
	    }
	@PostMapping("/flights")
	public ResponseEntity<FlightResponse> createFlight(@Valid @RequestBody FlightRequest request){
		FlightResponse response = flightService.createFlight(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PostMapping("/flights/search")
	public ResponseEntity<List<FlightResponse>> searchFlights(@RequestBody FlightRequest request) {
	    List<FlightResponse> responses = flightService.searchFlights(request);
	    return ResponseEntity.ok(responses);
	}
	
}
