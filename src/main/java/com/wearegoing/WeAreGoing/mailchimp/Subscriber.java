package com.wearegoing.WeAreGoing.mailchimp;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Subscriber {
	
	@JsonProperty("email_address")
	private String emailAddress;
	
	@JsonProperty("status")
	private String status;
	
	
	public Subscriber() {
		
	}
	
	public Subscriber(String emailAddress, String status) {
		this.emailAddress = emailAddress;
		this.status = status;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}

	public String getStatus() {
		return status;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
}
