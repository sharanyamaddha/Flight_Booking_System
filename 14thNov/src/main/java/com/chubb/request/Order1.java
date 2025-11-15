package com.chubb.request;

import org.hibernate.validator.constraints.Range;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
@Entity

public class Order1 {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id;
	@NotBlank
	private String item;
	@Min(value=1,message="price > 1")
	private int price;
	@Max(value=10,message="quantity < 10")
	@Range(min=1,max=4,message="range should be only between 1 and 4")
	private int quantity;
	
	 @ManyToOne(cascade = CascadeType.ALL)
	 @JoinColumn(name="address_id")
	 
	Address address;
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	

}
