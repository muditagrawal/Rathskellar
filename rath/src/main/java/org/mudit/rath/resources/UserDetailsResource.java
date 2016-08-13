package org.mudit.rath.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.mudit.rath.configuration.Role;
import org.mudit.rath.dao.UserDetailsDAO;
import org.mudit.rath.model.UserDetails;
import org.mudit.rath.security.Secured;

@Path("/userdetails")
public class UserDetailsResource {
	@GET
	@Path("/me")
	@Secured({Role.ADMIN,Role.CUSTOMER,Role.STAFF})
	@Produces(MediaType.APPLICATION_JSON)
	public UserDetails getCurrentUserDetails(@Context ContainerRequestContext requestContext ){
		UserDetailsDAO userDetailsDAO = new UserDetailsDAO();
		return userDetailsDAO.getCurrentUserDetails(requestContext);
	}
	
	@GET
	@Path("/{id}")
	@Secured({Role.ADMIN,Role.STAFF})
	@Produces(MediaType.APPLICATION_JSON)
	public UserDetails getUserDetailsById(@PathParam("id") int id){
		UserDetailsDAO userDetailsDAO = new UserDetailsDAO();
		return userDetailsDAO.getUserDetailsById(id);
	}
	
	@PUT
	@Path("/me")
	@Secured({Role.ADMIN,Role.CUSTOMER,Role.STAFF})
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserDetails updateCurrentUserDetails(UserDetails userDetails,@Context ContainerRequestContext requestContext){
		UserDetailsDAO userDetailsDAO = new UserDetailsDAO();
		return userDetailsDAO.updateCurrentUserDetails(userDetails,requestContext);
	}
	
	@DELETE
	@Path("/me")
	@Secured({Role.ADMIN,Role.CUSTOMER,Role.STAFF})
	public String deleteCurrentUserDetails(@Context ContainerRequestContext requestContext){
		UserDetailsDAO userDetailsDAO = new UserDetailsDAO();
		return userDetailsDAO.deleteCurrentUserDetails(requestContext);
	}
}
