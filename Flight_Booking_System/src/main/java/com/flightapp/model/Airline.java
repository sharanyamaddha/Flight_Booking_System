package com.flightapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Airline {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long airlineid;
	String name;
	public Long getAirlineid() {
		return airlineid;
	}
	public void setAirlineid(Long airlineid) {
		this.airlineid = airlineid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
