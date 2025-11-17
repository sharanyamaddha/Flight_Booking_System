package com.flightapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.flightapp.dto.request.FlightRequest;
import com.flightapp.dto.response.FlightResponse;
import com.flightapp.exceptions.ResourceNotFoundException;
import com.flightapp.interfaces.FlightService;
import com.flightapp.model.Airline;
import com.flightapp.model.Flight;
import com.flightapp.repository.AirlineRepository;
import com.flightapp.repository.FlightRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FlightServiceImpl implements FlightService{
	
	@Autowired
	FlightRepository flightRepository;
	
	@Autowired
	AirlineRepository airlineRepository;
	
	
	public FlightServiceImpl(FlightRepository flightRepository,
            AirlineRepository airlineRepository) {
		this.flightRepository = flightRepository;
		this.airlineRepository = airlineRepository;
}
	
	@Override
	 @Transactional
	public FlightResponse createFlight(FlightRequest request) {
		
		
		String name = request.getName() != null ? request.getName().trim() : "";
		 if (name.isEmpty()) {
	            throw new IllegalArgumentException("airlineName must be provided");
	        }

	        // 1) Try to find existing by name (case-insensitive)
		 Airline airline = airlineRepository.findByNameIgnoreCase(name)
	                .orElseGet(() -> {
	                    try {
	                        Airline a = new Airline();
	                        a.setName(name);
	                        return airlineRepository.save(a);
	                    } catch (DataIntegrityViolationException ex) {
	                        // another transaction created the same airline concurrently
	                        // re-query and return the existing one
	                        Optional<Airline> existing = airlineRepository.findByNameIgnoreCase(name);
	                        return existing.orElseThrow(() -> ex);
	                    }
	                });
		Flight flight=new Flight();
		flight.setAirline(airline);
		flight.setSource(request.getSource());
		flight.setDestination(request.getDestination());
		flight.setArrivalDatetime(request.getArrivalDatetime());
		flight.setDepartureDatetime(request.getDepartureDatetime());
		flight.setTotal_seats(request.getTotal_seats());
		
		flight.setAvailable_seats(request.getTotal_seats());
		flight.setPrice(request.getPrice());
		
		
		Flight saved=flightRepository.save(flight);
		
		FlightResponse res=new FlightResponse();
		res.setFlightid(saved.getFlightid());
		res.setAirlineid(saved.getAirline().getAirlineid());
		res.setSource(saved.getSource());
		res.setDestination(saved.getDestination());
		res.setDepartureDateTime(saved.getDepartureDatetime());
        res.setArrivalDateTime(saved.getArrivalDatetime());
        res.setTotal_seats(saved.getTotal_seats());
        res.setAvailable_seats(saved.getAvailable_seats());
        res.setPrice(saved.getPrice());
        
        return res;
		
	}
	
	@Override
	public List<FlightResponse> searchFlights(FlightRequest request) {

	    String source = request.getSource();
	    String destination = request.getDestination();
	    String airlineName = request.getName();

	    List<Flight> flights;

	    // CASE 1: source + destination + airlineName
	    if (source != null && destination != null && airlineName != null) {
	        flights = flightRepository.findBySourceAndDestinationAndAirline_NameIgnoreCase(
	                source, destination, airlineName);
	    }
	    // CASE 2: source + destination
	    else if (source != null && destination != null) {
	        flights = flightRepository.findBySourceAndDestination(source, destination);
	    }
	    // CASE 3: only airline
	    else if (airlineName != null) {
	        flights = flightRepository.findByAirline_NameIgnoreCase(airlineName);
	    }
	    // CASE 4: nothing provided
	    else {
	        flights = flightRepository.findAll();
	    }

	    // Map to Response List
	    List<FlightResponse> list = new ArrayList<>();
	    for (Flight f : flights) {
	        FlightResponse res = new FlightResponse();
	        res.setFlightid(f.getFlightid());
	        res.setAirlineid(f.getAirline().getAirlineid());
	        res.setSource(f.getSource());
	        res.setDestination(f.getDestination());
	        res.setDepartureDateTime(f.getDepartureDatetime());
	        res.setArrivalDateTime(f.getArrivalDatetime());
	        res.setTotal_seats(f.getTotal_seats());
	        res.setAvailable_seats(f.getAvailable_seats());
	        res.setPrice(f.getPrice());
	        list.add(res);
	    }

	    return list;
	}

}
