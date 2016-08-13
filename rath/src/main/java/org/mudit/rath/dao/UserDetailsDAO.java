package org.mudit.rath.dao;

import javax.ws.rs.container.ContainerRequestContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.mudit.rath.configuration.MySessionFactory;
import org.mudit.rath.model.User;
import org.mudit.rath.model.UserDetails;

public class UserDetailsDAO {
	public UserDetails getCurrentUserDetails(ContainerRequestContext requestContext){
		Session new_session = MySessionFactory.getMy_factory().openSession();
		User currentUser= (User)requestContext.getProperty("user");
		Criteria currentUserDetailsCriteria = new_session.createCriteria(UserDetails.class);
		new_session.beginTransaction();
		UserDetails cUserDetails = (UserDetails)currentUserDetailsCriteria.add(Restrictions.eq("user", currentUser)).uniqueResult();
		if (cUserDetails==null){
			System.out.println("User Details not found for current User");
			cUserDetails = new UserDetails();
		}
		new_session.close();
		return cUserDetails;
	}
	
	public UserDetails createNewUserDetails(UserDetails userDetails , ContainerRequestContext requestContext){
		UserDetails newUserDetails = new UserDetails();
		newUserDetails.setFirstname(userDetails.getFirstname());
		newUserDetails.setMiddlename(userDetails.getMiddlename());
		newUserDetails.setLastname(userDetails.getLastname());
		newUserDetails.setUser((User)requestContext.getProperty("user"));
		Session newSession = MySessionFactory.getMy_factory().openSession();
		newSession.beginTransaction();
		newSession.save(newUserDetails);
		newSession.getTransaction().commit();
		newSession.close();
		newUserDetails.getUser().setPassword("User Password");
		return newUserDetails;
	}
	
	public UserDetails updateCurrentUserDetails(UserDetails userDetails,ContainerRequestContext requestContext) {
		Session new_session = MySessionFactory.getMy_factory().openSession();
		User currentUser= (User)requestContext.getProperty("user");
		Criteria currentUserDetailsCriteria = new_session.createCriteria(UserDetails.class);
		new_session.beginTransaction();
		UserDetails cUserDetails = (UserDetails)currentUserDetailsCriteria.add(Restrictions.eq("user", currentUser)).uniqueResult();
		new_session.close();
		if (cUserDetails == null){
			return createNewUserDetails(userDetails,requestContext);
		}
		cUserDetails.setFirstname(userDetails.getFirstname());
		cUserDetails.setMiddlename(userDetails.getMiddlename());
		cUserDetails.setLastname(userDetails.getLastname());
		Session nSession = MySessionFactory.getMy_factory().openSession();
		nSession.beginTransaction();
		nSession.update(cUserDetails);
		nSession.getTransaction().commit();
		nSession.close();
		return cUserDetails;
	}
	
	public UserDetails getUserDetailsById(int id) {
		Session mSession = MySessionFactory.getMy_factory().openSession();
		UserDetails userDetails = (UserDetails)mSession.get(UserDetails.class, id);
		mSession.close();
		return userDetails;
	}
	
	public String deleteCurrentUserDetails(ContainerRequestContext requestContext){
		Session mSession = MySessionFactory.getMy_factory().openSession();
		User currentUser= (User)requestContext.getProperty("user");
		Criteria currentUserDetailsCriteria = mSession.createCriteria(UserDetails.class);
		mSession.beginTransaction();
		UserDetails cUserDetails = (UserDetails)currentUserDetailsCriteria.add(Restrictions.eq("user", currentUser)).uniqueResult();
		mSession.delete(cUserDetails);
		mSession.close();
		return "User Details have been deleted";
	}
}
