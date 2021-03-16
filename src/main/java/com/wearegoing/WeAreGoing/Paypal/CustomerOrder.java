package com.wearegoing.WeAreGoing.Paypal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;

//@Table(name = "CUSTOMER_ORDER")
//@Entity
public class CustomerOrder {
	
	@JsonProperty("order_id")
	private String orderID;
	
	@JsonProperty("payer_id") 
	private String payerID;
	
	@JsonProperty("token")
	private String token;
	
	public CustomerOrder() {
		
	}
	
	public CustomerOrder(String orderID, String payerID, String token) {
		this.orderID = orderID;
		this.payerID = payerID;
		this.token = token;
	}
	
	
	public String getOrderID() {
		return orderID;
	}
	
	public String getPayerID() {
		return payerID;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	
	public void setPayerID(String payerID) {
		this.payerID = payerID;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
}
