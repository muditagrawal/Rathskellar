package org.mudit.rath.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.mudit.rath.configuration.Role;
import org.mudit.rath.dao.AuthenticateUserDAO;
import org.mudit.rath.model.User;
import org.mudit.rath.security.Secured;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticateUser {
	@POST
	@Path("login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticateuser(User user){
		AuthenticateUserDAO authenticateUserDAO = new AuthenticateUserDAO();
		return authenticateUserDAO.authenticateUser(user);
		}
	
	@POST
	@Path("logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public Response unauthenticateuser(String access_token){
		AuthenticateUserDAO authenticateUserDAO = new AuthenticateUserDAO();
		return authenticateUserDAO.unauthenticateUser(access_token);
	}
	
	@POST
	@Path("register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User registerCustomer(User user){
		AuthenticateUserDAO authenticatedUser = new AuthenticateUserDAO();
		return authenticatedUser.registerUser(user,Role.CUSTOMER);
	}
	
	@POST
	@Secured({Role.ADMIN})
	@Path("registeradmin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User registerAdmin(User user){
		AuthenticateUserDAO authenticatedUser = new AuthenticateUserDAO();
		return authenticatedUser.registerUser(user,Role.ADMIN);
	}
	
	@POST
	@Secured({Role.ADMIN})
	@Path("registerstaff")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User registerStaff(User user){
		AuthenticateUserDAO authenticatedUser = new AuthenticateUserDAO();
		return authenticatedUser.registerUser(user,Role.STAFF);
	}
	
	@GET
	@Path("forgotpassword")
	public Boolean sendResetLink(User user){
		AuthenticateUserDAO authenticateUserDAO = new AuthenticateUserDAO();
		return authenticateUserDAO.sendResetLink(user);
	}
	
	@GET
	@Path("resetpassword/id/token")
	public User resetPassword(@PathParam("{token}") String token,@PathParam("{id}") int id,User user){
		AuthenticateUserDAO authenticateUserDAO = new AuthenticateUserDAO();
		return authenticateUserDAO.resetPassword(id,user,token);
	}
	@GET
	@Path("verifyaccount/{token}")
	public Response verifyAccount(@PathParam("token") String token){
		AuthenticateUserDAO authenticateUserDAO = new AuthenticateUserDAO();
		return authenticateUserDAO.verifyAccount(token);
	}
}
