package org.mudit.rath.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@XmlRootElement
public class Transaction {
	@Id @GeneratedValue
	private long id;
	private Date date;
	@OneToOne
	private UserOrder order;
	@OneToOne
	private CardDetails cardDetails;
	private float amount;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public UserOrder getOrder() {
		return order;
	}
	public void setOrder(UserOrder order) {
		this.order = order;
	}
	public CardDetails getCardDetails() {
		return cardDetails;
	}
	public void setCardDetails(CardDetails cardDetails) {
		this.cardDetails = cardDetails;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	
}
