package com.flightapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.dto.request.BookingRequest;
import com.flightapp.dto.response.BookingResponse;
import com.flightapp.interfaces.BookingService;

import java.util.List;

import jakarta.validation.Valid;

@RestController
public class BookingController {

	 private  BookingService bookingService;
	 public BookingController(BookingService bookingService) {
	        this.bookingService = bookingService;
	    }

	    @PostMapping("/bookings/{flightid}")
	    public ResponseEntity<BookingResponse> createBooking(
	            @PathVariable Long flightid,
	            @Valid @RequestBody BookingRequest request) {
	        
	        BookingResponse response = bookingService.createBooking(flightid, request);
	        return ResponseEntity.status(HttpStatus.CREATED).body(response);
	    }

	    @GetMapping("/bookings/{pnr}")
	    public ResponseEntity<BookingResponse> getBookingByPnr(@PathVariable String pnr) {
	        BookingResponse response = bookingService.getBookingByPnr(pnr);
	        return ResponseEntity.ok(response);
	    }

	    @GetMapping("/bookings/history/{email}")
	    public ResponseEntity<List<BookingResponse>> getBookingHistory(@PathVariable String email) {
	        List<BookingResponse> responses = bookingService.getBookingHistoryByEmail(email);
	        return ResponseEntity.ok(responses);
	    }

	    @DeleteMapping("/bookings/{pnr}/cancel")
	    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable String pnr) {
	        BookingResponse response = bookingService.cancelBooking(pnr);
	        return ResponseEntity.ok(response);
	    }
	
}
