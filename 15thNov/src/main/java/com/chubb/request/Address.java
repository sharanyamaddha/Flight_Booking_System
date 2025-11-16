package com.chubb.request;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Address {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	long addressID;
	public long getAddressID() {
		return addressID;
	}
	public void setAddressID(long adressID) {
		this.addressID = adressID;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	String city;
	
}
