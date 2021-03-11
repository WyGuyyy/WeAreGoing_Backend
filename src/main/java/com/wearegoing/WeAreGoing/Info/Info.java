package com.wearegoing.WeAreGoing.Info;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "info")
public class Info {
	
	@JsonProperty("info_id")
	private int infoID;
	
	@JsonProperty("amount")
	private int amount;
	
	@JsonProperty("receiver")
	private String receiver;
	
	@JsonProperty("link")
	private String link;
	
	public Info() {
		
	}
	
	public Info(int amount, String receiver, String link) {
		this.amount = amount;
		this.receiver = receiver;
		this.link = link;
	}
	
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "info_id")
	public int getInfoID() {
		return infoID;
	}
	
	@Column(name = "amount")
	public int getAmount() {
		return amount;
	}
	
	@Column(name = "receiver")
	public String getReceiver() {
		return receiver;
	}
	
	@Column(name = "link")
	public String getLink() {
		return link;
	}
	
	public void setInfoID(int infoID) {
		this.infoID = infoID;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
}
