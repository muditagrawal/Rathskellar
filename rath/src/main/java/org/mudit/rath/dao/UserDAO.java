package org.mudit.rath.dao;

import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;

import org.hibernate.Session;

import org.mudit.rath.configuration.MySessionFactory;
import org.mudit.rath.model.User;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class UserDAO {
	public List<User> getAllUsers(ContainerRequestContext requestContext){
		if(MySessionFactory.getMy_factory() != null){
    		Session new_session = MySessionFactory.getMy_factory().openSession();
        	new_session.beginTransaction();
        	List<User> all_users = (List<User>)new_session.createCriteria(User.class).list();
        	for (User user : all_users){
        		user.setPassword("User Password");
        	}
        	new_session.close();
    	return all_users;
    	}
		return null;
	}
	
	public User getUserById(int id){
		Session new_session = MySessionFactory.getMy_factory().openSession();
		new_session.beginTransaction();
		User user = (User)new_session.get(User.class, id);
		new_session.close();
		user.setPassword("User Password");
		return user;
	}
	
	public User getCurrentUser(ContainerRequestContext requestContext) {
		Session new_session = MySessionFactory.getMy_factory().openSession();
		new_session.beginTransaction();
		User user = (User)requestContext.getProperty("user");
		new_session.close();
		user.setPassword("User Password");
		return user;
	}
	
	public User updateCurrentUser(User user, ContainerRequestContext requestContext) {
		Session new_session = MySessionFactory.getMy_factory().openSession();
		new_session.beginTransaction();
		User old_user = (User)requestContext.getProperty("user");
		String hashed  = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		old_user.setPassword(hashed);
		new_session.update(old_user);
		new_session.getTransaction().commit();
		new_session.close();
		old_user.setPassword("Updated Password");
		return old_user;
		
	}
	
	public String deleteCurrentUser(ContainerRequestContext requestContext) {
		Session new_session = MySessionFactory.getMy_factory().openSession();
		new_session.beginTransaction();
		User user =(User)requestContext.getProperty("user");
		new_session.delete(user);
		new_session.getTransaction().commit();
		new_session.close(); 
    	return "user deleted";
		
	}
}
