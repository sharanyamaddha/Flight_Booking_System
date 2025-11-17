package com.flightapp.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FlightResponse {
	
	Long flightid;
	Long airlineid;
	String source;
	String destination;
	LocalDateTime departureDateTime;
   LocalDateTime arrivalDateTime;
   int total_seats;
	 int available_seats;
	 Double price;

}
