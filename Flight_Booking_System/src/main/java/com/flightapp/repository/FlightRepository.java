package com.flightapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightapp.model.Flight;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight ,Long>{

	List<Flight> findBySourceAndDestination(String source, String destination);

	List<Flight> findByAirline_NameIgnoreCase(String name);

	List<Flight> findBySourceAndDestinationAndAirline_NameIgnoreCase(
	        String source, String destination, String airlineName);

}
