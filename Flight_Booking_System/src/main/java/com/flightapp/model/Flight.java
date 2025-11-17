package com.flightapp.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Flight {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long flightid;
	
	@ManyToOne
	 @JoinColumn(name = "airline_id", nullable=false)
	Airline airline;
	
	String flightno;
	String source;
	String destination;
	LocalDateTime departureDatetime;
	 LocalDateTime arrivalDatetime;
	 int total_seats;
	 int available_seats;
	 Double price;
	 String trip_type;
	 String name;
	 
	 
	public String getName() {
		return name;
	}
	 public void setName(String name) {
		 this.name = name;
	 }
	public Long getFlightid() {
		return flightid;
	}
	public void setFlightid(Long flightid) {
		this.flightid = flightid;
	}
	public Airline getAirline() {
		return airline;
	}
	public void setAirline(Airline airline) {
		this.airline = airline;
	}
	public String getFlightno() {
		return flightno;
	}
	public void setFlightno(String flightno) {
		this.flightno = flightno;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public LocalDateTime getDepartureDatetime() {
		return departureDatetime;
	}
	public void setDepartureDatetime(LocalDateTime departureDatetime) {
		this.departureDatetime = departureDatetime;
	}
	public LocalDateTime getArrivalDatetime() {
		return arrivalDatetime;
	}
	public void setArrivalDatetime(LocalDateTime arrivalDatetime) {
		this.arrivalDatetime = arrivalDatetime;
	}
	public int getTotal_seats() {
		return total_seats;
	}
	public void setTotal_seats(int total_seats) {
		this.total_seats = total_seats;
	}
	public int getAvailable_seats() {
		return available_seats;
	}
	public void setAvailable_seats(int available_seats) {
		this.available_seats = available_seats;
	}
	
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getTrip_type() {
		return trip_type;
	}
	public void setTrip_type(String trip_type) {
		this.trip_type = trip_type;
	}

	
	
}
