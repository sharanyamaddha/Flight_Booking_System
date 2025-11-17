package com.flightapp.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PassengerRequest {

	@NotEmpty
	String name;
	
	@NotNull
	String gender;
	
	@NotNull
	int age;
	
	@NotNull @NotEmpty
	String seat_no;
	
	@NotNull
	String meal_type;
	
}
