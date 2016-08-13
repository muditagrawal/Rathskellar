package org.mudit.rath.dao;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Key;
import java.util.Date;
import java.util.Properties;

import javax.crypto.spec.SecretKeySpec;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.mudit.rath.configuration.MySessionFactory;
import org.mudit.rath.configuration.Role;
import org.mudit.rath.model.User;
import org.mudit.rath.model.UserSession;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.sun.mail.smtp.SMTPMessage;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticateUserDAO {
	public Response authenticateUser(User user){
		try{
		String token;
		Session new_session = MySessionFactory.getMy_factory().openSession();
		new_session.beginTransaction();
		Criteria criteria = new_session.createCriteria(User.class);
		User matched_user = (User) criteria.add(Restrictions.eq("username", user.getUsername())).uniqueResult();
		new_session.close();
		if ((matched_user.getUsername().equals(user.getUsername()))&&(BCrypt.checkpw(user.getPassword(), matched_user.getPassword()))){
			token = issueToken(user,12000000);
		}
		else{
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		new_session = MySessionFactory.getMy_factory().openSession();
		UserSession user_session = new UserSession();
		user_session.setUser(matched_user);
		user_session.setAccess_token(token);
		new_session.beginTransaction();
		new_session.save(user_session);
		new_session.getTransaction().commit();
		new_session.close();
		matched_user.setPassword("User Password");
		return Response.ok()
				.entity(user_session)
				.build();
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
	public String issueToken(User user,int time){
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("MysecretKey");
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		JwtBuilder builder = Jwts.builder().setId(user.getUsername())
                .setIssuedAt(now)
                .setSubject(user.getUsername()+user.getRole())
                .setIssuer("Mudit")
                .signWith(signatureAlgorithm, signingKey);
				
	    long expMillis = nowMillis + time;
	    Date exp = new Date(expMillis);
	    builder.setExpiration(exp);
	   return builder.compact();
	}
	
	public User registerUser(User user ,Role role){
		if(MySessionFactory.getMy_factory() != null){
    		String hashed  = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
    		user.setPassword(hashed);
    		user.setRole(role);
    		Session new_session = MySessionFactory.getMy_factory().openSession();
    		new_session.beginTransaction();
    		new_session.save(user);
    		new_session.getTransaction().commit();
    		User created_user = new_session.get(User.class, user.getId());
    		new_session.close();
    		created_user.setPassword("Your Password");
    		Properties properties = System.getProperties();
			 properties.put("mail.smtp.host", "smtp.gmail.com");
			 properties.put("mail.smtp.socketFactory.port", "465");
			 properties.put("mail.smtp.socketFactory.class",
			    "javax.net.ssl.SSLSocketFactory");
			 properties.put("mail.smtp.auth", "true");
			 properties.put("mail.smtp.port", "805");
    		javax.mail.Session session = javax.mail.Session.getDefaultInstance(properties
    				,new javax.mail.Authenticator(){
                		@Override
                		protected PasswordAuthentication getPasswordAuthentication(){
                			return new PasswordAuthentication("mudit.agrawal.2007@gmail.com","MyEmailIdPassword");
                		}
    		});
    		String token = issueToken(user,86400000);
    		UserSession verification_session = new UserSession();
    		verification_session.setUser(created_user);
    		verification_session.setAccess_token(token);
    		new_session =MySessionFactory.getMy_factory().openSession();
    		new_session.beginTransaction();
    		new_session.save(verification_session);
    		new_session.getTransaction().commit();
    		new_session.close();
    		String url="http://localhost:8080/rath/webapi/verifyaccount/";
    		url = url+ verification_session.getAccess_token();
    		try{
    			SMTPMessage message = new SMTPMessage(session);
    			message.setFrom(new InternetAddress("mudit.agrawal.2007@gmail.com"));
    			message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
    			message.setSubject("Rathskellar New Account Verification");
    			message.setText("Click the link below to verify your Email "+url);
                message.setNotifyOptions(SMTPMessage.NOTIFY_SUCCESS);
                int returnOption = message.getReturnOption();
                 System.out.println(returnOption); 
    			Transport.send(message);
    			System.out.println("Sent message successfully....");
    		}catch (MessagingException mex) {
    	         mex.printStackTrace();
    	      }
    		return created_user;
    	}
    	return null;
	}
	public Response verifyAccount(String token){
		Claims claims = Jwts.parser()         
	    		   .setSigningKey(DatatypeConverter.parseBase64Binary("MysecretKey"))
	    		   .parseClaimsJws(token).getBody();
		Session mSession = MySessionFactory.getMy_factory().openSession();
		mSession.beginTransaction();
		Criteria verification_criterion = mSession.createCriteria(org.mudit.rath.model.UserSession.class);
		org.mudit.rath.model.UserSession session =(org.mudit.rath.model.UserSession) verification_criterion.add(Restrictions.eq("access_token", token)).uniqueResult();
		if (session!=null){
			User user = session.getUser();
			user.setVerified(true);
			mSession.save(user);
			mSession.delete(session);
			mSession.getTransaction().commit();
			mSession.close();
			try {
				return Response.temporaryRedirect(new URI("http://localhost:8080/rath/emailverified")).build();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			mSession.close();
			return Response.status(Response.Status.GATEWAY_TIMEOUT).build();
		}
		return Response.status(Response.Status.GATEWAY_TIMEOUT).build();
	}
	public Boolean sendResetLink(User user){
		try{
			Session mSession = MySessionFactory.getMy_factory().openSession();
			User user2 = (User)mSession.createCriteria(User.class).add(Restrictions.eqOrIsNull("email", user.getUsername()));
		}catch (Exception e){
			
		}
		return false;
	}
	
	public User resetPassword(int id, User user, String token){
		return null;
	}

	public Response unauthenticateUser(String access_token) {
		System.out.println(access_token);
		// TODO Auto-generated method stub
		try{
			Session nSession = MySessionFactory.getMy_factory().openSession();
			nSession.beginTransaction();
			Criteria criteria = nSession.createCriteria(UserSession.class);
			UserSession userSession = (UserSession) criteria.add(Restrictions.eq("access_token", access_token)).uniqueResult();
			nSession.delete(userSession);
			nSession.getTransaction().commit();
			nSession.close();
			return Response
					.ok()
					.build();
			}
		catch (Exception e) {
			e.printStackTrace();
				// TODO: handle exception
			return Response.status(Response.Status.UNAUTHORIZED).build();
			}
	}
}
