package com.flightapp.dto.response;

import java.time.LocalDateTime;

import lombok.Data;
import java.util.List;

import com.flightapp.model.Flight;

@Data
public class BookingResponse {
	
	String pnr;
	String status;
	Long flightid;
	Double totalprice;
	LocalDateTime bookingDateTime;
	List<PassengerResponse> passengers;

	
}
