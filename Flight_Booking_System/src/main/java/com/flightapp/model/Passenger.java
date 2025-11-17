package com.flightapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames = {"flight_id", "seat_no"})
)
public class Passenger {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long passengerid;
	
	@ManyToOne
	@JoinColumn(name="flight_id",nullable=false)
	 Flight flight;
	 
	@ManyToOne
	@JoinColumn(name = "pnr", referencedColumnName = "pnr", nullable = false, insertable = false, updatable = false)
	 Booking booking;
	
	@Column(name = "pnr", nullable = false)
	String pnr;
	public String getPnr() {
		return pnr;
	}
	public void setPnr(String pnr) {
		this.pnr = pnr;
	}
	String name;
	int age;
	String gender;
	String seat_no;
	String meal_type;
	public Long getPassengerid() {
		return passengerid;
	}
	public void setPassengerid(Long passengerid) {
		this.passengerid = passengerid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getSeat_no() {
		return seat_no;
	}
	public void setSeat_no(String seatNo) {
		this.seat_no = seatNo;
	}
	public String getMeal_type() {
		return meal_type;
	}
	public void setMeal_type(String meal_type) {
		this.meal_type = meal_type;
	}
	public Flight getFlight() {
		return flight;
	}
	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	public Booking getBooking() {
		return booking;
	}
	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	

}
