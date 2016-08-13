package org.mudit.rath.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class DummyBankAccount {
	@Id
	@GeneratedValue
	private long id;
	private float balance;
	@OneToOne
	private CardDetails cardDetails;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public CardDetails getCardDetails() {
		return cardDetails;
	}
	public void setCardDetails(CardDetails cardDetails) {
		this.cardDetails = cardDetails;
	}

}
