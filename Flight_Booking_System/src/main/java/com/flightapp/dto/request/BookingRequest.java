package com.flightapp.dto.request;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingRequest {

//	@NotNull
//	Long flightid;
	
	@NotNull @NotEmpty @Email
	String booker_emailid;
	
	@NotNull
	String paymentmode;
	
	@NotNull
	List<PassengerRequest> passengers;
}
