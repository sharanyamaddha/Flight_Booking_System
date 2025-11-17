package com.flightapp.interfaces;

import com.flightapp.dto.request.FlightRequest;
import java.util.List;
import com.flightapp.dto.response.FlightResponse;

public interface FlightService {

	FlightResponse createFlight(FlightRequest request);
	
	List<FlightResponse> searchFlights(FlightRequest request);
}
