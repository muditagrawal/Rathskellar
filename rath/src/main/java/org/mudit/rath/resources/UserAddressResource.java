package org.mudit.rath.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.mudit.rath.configuration.Role;
import org.mudit.rath.dao.UserAddressDAO;
import org.mudit.rath.model.Address;
import org.mudit.rath.security.Secured;

@Path("/address")
public class UserAddressResource {
	@GET
	@Path("/me")
	@Secured({Role.ADMIN,Role.CUSTOMER,Role.STAFF})
	@Produces(MediaType.APPLICATION_JSON)
	public List<Address> getCurrentUserAddress(@Context ContainerRequestContext requestContext ){
		UserAddressDAO userAddressDAO = new UserAddressDAO();
		return userAddressDAO.getCurrentUserAddress(requestContext);
	}
}
