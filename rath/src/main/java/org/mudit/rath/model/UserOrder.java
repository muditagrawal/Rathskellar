package org.mudit.rath.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import org.mudit.rath.configuration.OrderType;

@Entity
@XmlRootElement
public class UserOrder {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	private User user;
	@OneToMany
	private List<OrderItems> items;
	private OrderType orderType;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public List<OrderItems> getItems() {
		return items;
	}
	
	public void setItems(List<OrderItems> items) {
		this.items = items;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	@Override
	public String toString() {
		return "UserOrder [id=" + id + ", user=" + user + ", items=" + items + ", orderType=" + orderType + "]";
	}
	
}
