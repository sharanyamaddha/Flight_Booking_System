package com.flightapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightapp.model.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long>{

	// boolean existsByFlight_FlightidAndSeat_No(Long flightid, String seat_no);
}
