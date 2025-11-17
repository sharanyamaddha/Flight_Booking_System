package com.flightapp.interfaces;

import com.flightapp.dto.request.BookingRequest;
import com.flightapp.dto.response.BookingResponse;

import java.util.List;

public interface BookingService {

	BookingResponse createBooking(Long flightid, BookingRequest request);
	
	BookingResponse getBookingByPnr(String pnr);
	
	
	
	BookingResponse cancelBooking(String pnr);

	List<BookingResponse> getBookingHistoryByEmail(String email);
	
}
