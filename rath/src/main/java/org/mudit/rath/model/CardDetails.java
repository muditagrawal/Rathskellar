package org.mudit.rath.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class CardDetails {
	@Id @GeneratedValue
	private long id;
	private String holderName;
	
	private String cardNo;
	private int cVV;
	private Date expiryDate;
	private Date issueDate;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public int getcVV() {
		return cVV;
	}
	public void setcVV(int cVV) {
		this.cVV = cVV;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	public String getHolderName() {
		return holderName;
	}
	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}
	@Override
	public String toString() {
		return "CardDetails [id=" + id + ", holderName=" + holderName + ", cardNo=" + cardNo + ", cVV=" + cVV
				+ ", expiryDate=" + expiryDate + ", issueDate=" + issueDate + "]";
	}
	
}
