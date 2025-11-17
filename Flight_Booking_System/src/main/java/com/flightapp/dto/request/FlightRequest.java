package com.flightapp.dto.request;

import java.time.LocalDateTime;

import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FlightRequest {

//	@NotNull
//	Long airlineId;
	
	@NotNull
	String name;
	
	@NotEmpty
	String source;
	@NotEmpty
	String destination;
	
	@NotNull
	LocalDateTime departureDatetime;
	
	@NotNull
	 LocalDateTime arrivalDatetime;
	
	@NotNull
	 int total_seats;
	
	@NotNull
	 Double price;
	
}
