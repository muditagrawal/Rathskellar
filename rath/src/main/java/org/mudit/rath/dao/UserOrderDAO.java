package org.mudit.rath.dao;

import java.util.List;

import org.hibernate.Session;

import org.mudit.rath.configuration.MySessionFactory;
import org.mudit.rath.model.OrderItems;
import org.mudit.rath.model.UserOrder;

public class UserOrderDAO {
	public List<UserOrder> getAllOrders(){
		Session mSession = MySessionFactory.getMy_factory().openSession();
		mSession.beginTransaction();
		List<UserOrder> userOrders = (List<UserOrder>)mSession.createCriteria(UserOrder.class).list();
		mSession.close();
		return userOrders;
	}
	
	public UserOrder createNewUserOrder(UserOrder userOrder){
		Session mSession = MySessionFactory.getMy_factory().openSession();
		mSession.beginTransaction();
		for (OrderItems item : userOrder.getItems()) {
			mSession.save(item);
		}
		mSession.getTransaction().commit();
		mSession.beginTransaction();
		mSession.save(userOrder);
		mSession.getTransaction().commit();
		mSession.close();
		System.out.println(userOrder.getId());
		return userOrder;
		
	}
	
	public UserOrder updateUserOrderById(int id,UserOrder userOrder) {
		Session mSession = MySessionFactory.getMy_factory().openSession();
		mSession.beginTransaction();
		UserOrder currentUserOrder = (UserOrder)mSession.get(UserOrder.class, id);
		currentUserOrder.setItems(userOrder.getItems());
		currentUserOrder.setOrderType(userOrder.getOrderType());
		mSession.update(currentUserOrder);
		mSession.getTransaction().commit();
		mSession.close();
		return currentUserOrder;
		
	}
	
}
