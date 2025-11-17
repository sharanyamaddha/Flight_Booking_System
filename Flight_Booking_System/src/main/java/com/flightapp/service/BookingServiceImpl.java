package com.flightapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightapp.dto.request.BookingRequest;
import com.flightapp.dto.request.PassengerRequest;
import com.flightapp.dto.response.BookingResponse;
import com.flightapp.dto.response.PassengerResponse;
import com.flightapp.exceptions.ResourceNotFoundException;
import com.flightapp.exceptions.SeatAlreadyTakenException;
import com.flightapp.interfaces.BookingService;
import com.flightapp.model.Booking;
import com.flightapp.model.Flight;
import com.flightapp.model.Passenger;
import com.flightapp.repository.BookingRepository;
import com.flightapp.repository.FlightRepository;
import com.flightapp.repository.PassengerRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {
	
	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private PassengerRepository passengerRepository;
	
	public BookingServiceImpl(FlightRepository flightRepository,
            BookingRepository bookingRepository,
            PassengerRepository passengerRepository) {
		this.flightRepository = flightRepository;
		this.bookingRepository = bookingRepository;
		this.passengerRepository = passengerRepository;
		}
	
	   @Transactional
	@Override
	//BOOKING A FLIGHT 
	 public BookingResponse createBooking(Long flightid, BookingRequest request) {
	    
		//check flight exists or not
		Flight flight=flightRepository.findById(flightid)
				.orElseThrow(()->new ResourceNotFoundException("Flight not found with id: " + flightid));
		
		//check seat availability
//		for(PassengerRequest preq:request.getPassengers()) {
//			boolean flag=bookingRepository.existsByFlight_FlightidAndSeat_No(request.getFlightid(),preq.getSeat_no());
//			
//			if(flag) {
//				throw new SeatAlreadyTakenException("Seat " + preq.getSeat_no() + " is already booked for flight " + request.getFlightid());
//				}
//			}
		
		//genreate pnr
	    String pnr;
	    do {
	        pnr = "PNR" + System.currentTimeMillis();
	    } while (bookingRepository.existsByPnr(pnr));

		
		//create booking entity
		Booking booking = new Booking();
		booking.setPnr(pnr);
		booking.setFlight(flight);
		booking.setBookerEmailId(request.getBooker_emailid());
		booking.setStatus("BOOKED");
		booking.setBookingDateTime(LocalDateTime.now());
		
		//calculate total amount 
		double totalAmount = (flight.getPrice() == null ? 0.0 : flight.getPrice()) * request.getPassengers().size();
        booking.setTotal_amount(totalAmount);
        
        //save booking
        Booking savedBooking = bookingRepository.saveAndFlush(booking);
        
        Booking persistedBooking = bookingRepository.findByPnr(savedBooking.getPnr())
                .orElseThrow(() -> new ResourceNotFoundException("Booking was not persisted for PNR: " + savedBooking.getPnr()));

        //save passengers linke dto booking
        List<Passenger> savedPassengers = new ArrayList<>();
        for (PassengerRequest pReq : request.getPassengers()) {
            Passenger passenger = new Passenger();
            passenger.setName(pReq.getName());
            passenger.setAge(pReq.getAge());
            passenger.setGender(pReq.getGender());
            passenger.setSeat_no(pReq.getSeat_no());
            passenger.setMeal_type(pReq.getMeal_type());
            passenger.setBooking(persistedBooking);
            passenger.setFlight(flight);
            passenger.setPnr(persistedBooking.getPnr());

            Passenger saved = passengerRepository.save(passenger);
            savedPassengers.add(saved);
        }
        
        
        
        //Map to BookingResponse dto
        BookingResponse response = new BookingResponse();
        response.setPnr(savedBooking.getPnr());
        response.setStatus(savedBooking.getStatus());
        response.setFlightid(flight.getFlightid());
        response.setTotalprice(persistedBooking.getTotal_amount());
        response.setBookingDateTime(persistedBooking.getBookingDateTime());

        List<PassengerResponse> pres = new ArrayList<>();
        for (Passenger p : savedPassengers) {
            PassengerResponse pr = new PassengerResponse();
            pr.setName(p.getName());
            pr.setAge(p.getAge());
            pr.setGender(p.getGender());
            pr.setSeat_no(p.getSeat_no());
            pres.add(pr);
        }
        response.setPassengers(pres);

        return response;
	    }

	
	
	   //@Transactional(readOnly=true)
	    @Override
	    //GET BOOKING BY PNR
	    public BookingResponse getBookingByPnr(String pnr) {
	        Booking booking =bookingRepository.findByPnr(pnr)
	                .orElseThrow(() -> new ResourceNotFoundException("Booking not found for PNR: " + pnr));
	        return mapToBookingResponse(booking);
	        
	    }
	        
	    // mapping helper
	        private BookingResponse mapToBookingResponse(Booking booking) {
	        BookingResponse res=new BookingResponse();
	        
	        res.setPnr(booking.getPnr());
	        res.setStatus(booking.getStatus());
	        res.setTotalprice(booking.getTotal_amount());
	        res.setBookingDateTime(booking.getBookingDateTime());
	        res.setFlightid(booking.getFlight().getFlightid());
	        
	        //mapping passengers
	        
	        //creating empty passenger list
	        List<PassengerResponse> passengerResponses = new ArrayList<>();
	        //get all passengers from db
	        List<Passenger> passengers = booking.getPassengers();
	        if (passengers != null) {
	            for (Passenger p : passengers) {
	                PassengerResponse pr = new PassengerResponse();
	                pr.setName(p.getName());
	                pr.setAge(p.getAge());
	                pr.setGender(p.getGender());
	                pr.setSeat_no(p.getSeat_no());
	                // pr.setMealType(p.getMealType());
	                passengerResponses.add(pr);
	            }
	        }
	        res.setPassengers(passengerResponses);
	        
	        return res;
	        }
 
	        
	    
	    
	    
	    
	        @Transactional
	    @Override
	    //GET BOOKING HISTORY BY EMAIL
	    public List<BookingResponse> getBookingHistoryByEmail(String booker_emailid) {
	    	
	    	//first fetch bookings from repo
	    	 List<Booking> bookings = bookingRepository.findByBookerEmailIdOrderByBookingDateTimeDesc(booker_emailid);
	    	 
	    	 //mapping
	    	    List<BookingResponse> responses = new ArrayList<>();
	    	    
	    	    if (bookings != null && !bookings.isEmpty()) {
	    	        for (Booking b : bookings) {
	    	            responses.add(mapToBookingResponse(b)); 
	    	        }
	    	    }
	    	    return responses;

	    	
	    }
	    
	    
	    
	    

	    @Override
	    //CANCEL BOOKING BY PNR
	    public BookingResponse cancelBooking(String pnr) {
	    	Booking booking = bookingRepository.findByPnr(pnr)
	                .orElseThrow(() -> new ResourceNotFoundException("Booking not found for PNR: " + pnr));

	    	
	    	//if already cancelled, return currrent state
	    	if("CANCELLED".equalsIgnoreCase(booking.getStatus())) {
	    		return mapToBookingResponse(booking);
	    	}
	    	
	    	booking.setStatus("CANCELLED");
	    	
	    	Flight flight=booking.getFlight();
	    	if(flight!=null) {
	    		int seatsTorelease = booking.getPassengers()== null ? 0 : booking.getPassengers().size();
	    		Integer avail=flight.getAvailable_seats();
	    		flight.setAvailable_seats(avail + seatsTorelease);
	    		 flightRepository.save(flight);
	    		
	    	}
	    	
	    	Booking updated=bookingRepository.save(booking);
	    	
	    	return mapToBookingResponse(updated);
	    	
	    }
	
}
