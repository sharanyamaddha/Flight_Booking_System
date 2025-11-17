package com.flightapp.model;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.ArrayList;

@Entity
public class Booking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@Column(nullable = false, unique = true)
	String pnr;
	
	 public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	 @ManyToOne
	 @JoinColumn(name = "flight_id", nullable=false)
	Flight flight;
	LocalDateTime bookingDateTime;
	String bookerEmailId;
	int seats_booked;
	double total_amount;
	String status;
	
	@OneToMany(mappedBy="booking",cascade=CascadeType.ALL)
	List<Passenger> passengers=new ArrayList<>();
	
	public String getPnr() {
		return pnr;
	}
	public List<Passenger> getPassengers() {
		return passengers;
	}
	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}
	public void setPnr(String pnr) {
		this.pnr = pnr;
	}
	public Flight getFlight() {
		return flight;
	}
	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	
	
	public LocalDateTime getBookingDateTime() {
		return bookingDateTime;
	}
	public void setBookingDateTime(LocalDateTime bookingDateTime) {
		this.bookingDateTime = bookingDateTime;
	}
	public String getBookerEmailId() {
		return bookerEmailId;
	}
	public void setBookerEmailId(String bookerEmailId) {
		this.bookerEmailId = bookerEmailId;
	}
	public int getSeats_booked() {
		return seats_booked;
	}
	public void setSeats_booked(int seats_booked) {
		this.seats_booked = seats_booked;
	}
	public  double getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(double totalAmount) {
		this.total_amount = totalAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
