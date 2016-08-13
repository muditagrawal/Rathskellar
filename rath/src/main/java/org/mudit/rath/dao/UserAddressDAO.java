package org.mudit.rath.dao;

import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.mudit.rath.configuration.MySessionFactory;
import org.mudit.rath.model.Address;
import org.mudit.rath.model.User;

public class UserAddressDAO {
	public  List<Address> getCurrentUserAddress(@Context ContainerRequestContext requestContext) {
		Session new_session = MySessionFactory.getMy_factory().openSession();
		User currentUser= (User)requestContext.getProperty("user");
		Criteria currentUserAddressCriteria = new_session.createCriteria(Address.class);
		new_session.beginTransaction();
		List<Address> cUserAddress = (List<Address>)currentUserAddressCriteria.add(Restrictions.eq("user", currentUser)).list();
		new_session.close();
		return cUserAddress;
	}
	
	public Address createCurrentUserAddress(Address mAddress,@Context ContainerRequestContext requestContext){
		Address newAddress = new Address();
		User currentUser= (User)requestContext.getProperty("user");
		newAddress.setAptNo(mAddress.getAptNo());
		newAddress.setStreet(mAddress.getStreet());
		newAddress.setUser(currentUser);
		newAddress.setCity(mAddress.getCity());
		Session mSession = MySessionFactory.getMy_factory().openSession();
		mSession.beginTransaction();
		mSession.save(newAddress);
		mSession.getTransaction().commit();
		mSession.close();
		return newAddress;
	}
}
