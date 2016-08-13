package org.mudit.rath.resources;

import java.util.List;

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
import org.mudit.rath.dao.UserDAO;
import org.mudit.rath.model.User;
import org.mudit.rath.security.Secured;

/**
 * Root resource (exposed at "user" path)
 */
@Path("user")
public class UserResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
	@Secured({Role.ADMIN})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllUsers(@Context ContainerRequestContext requestContext) {
    	UserDAO userDAO = new UserDAO();
    	return userDAO.getAllUsers(requestContext);
    }
        
    @Path("/id")
    @GET
    @Secured({Role.ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserById(@PathParam("id") int id){
    	UserDAO userDAO = new UserDAO();
		return userDAO.getUserById(id);
    }
    
    @Path("/me")
    @GET
    @Secured({Role.ADMIN,Role.STAFF,Role.CUSTOMER})
    @Produces(MediaType.APPLICATION_JSON)
    public User getCurrentUser(@Context ContainerRequestContext requestContext){
    	UserDAO userDAO = new UserDAO();
    	return userDAO.getCurrentUser(requestContext);
    }
    
    @Path("/me")
    @PUT
    @Secured({Role.ADMIN,Role.CUSTOMER,Role.STAFF})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User updateCurrentUser(User user,@Context ContainerRequestContext requestContext){
    	UserDAO userDAO = new UserDAO();
    	return userDAO.updateCurrentUser(user,requestContext);
    	
    }
    
    @Path("/me")
    @DELETE
    @Secured({Role.ADMIN,Role.STAFF,Role.CUSTOMER})
    public String deleteCurrentUser(@Context ContainerRequestContext requestContext){
    	UserDAO userDAO = new UserDAO();
    	return userDAO.deleteCurrentUser(requestContext);
    	
    }
}
