package com.myapp.spring.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="springboot_products")


public class Product {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BAGGAGE_ID")
	private Integer baggageId;
	
	@Column(name = "FIRST_NAME",nullable = false)
	private String firstName;
	
	@Column(name = "PNR")
	private String pnr;
	
	@Column(name = "Seat_NO")
	private Integer seatno;
	
	@Column(name = "USER_EMAIL")
	private String email;
	
	public Product() {
		// TODO Auto-generated constructor stub
	}

	public Product(String firstName, String pnr, Integer seatno, String email) {
		this.firstName = firstName;
		this.pnr = pnr;
		this.seatno = seatno;
		this.email = email;
	}

	
	

	public Integer getBaggageId() {
		return baggageId;
	}

	public void setBaggageId(Integer baggageId) {
		this.baggageId = baggageId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	

	public Integer getSeatno() {
		return seatno;
	}

	public void setSeatno(Integer seatno) {
		this.seatno = seatno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		return Objects.hash(pnr, seatno, baggageId, firstName, email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Product))
			return false;
		Product other = (Product) obj;
		return Objects.equals(pnr, other.pnr) && Objects.equals(seatno, other.seatno)
				&& Objects.equals(baggageId, other.baggageId) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(email, other.email);
	}
	
	
	
	

}
