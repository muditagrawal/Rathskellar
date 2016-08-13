package org.mudit.rath.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.mudit.rath.dao.UserOrderDAO;
import org.mudit.rath.model.UserOrder;

@Path("/order")
public class UserOrderResource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserOrder> getAllOrders(){
		UserOrderDAO userOrderDAO = new UserOrderDAO();
		return userOrderDAO.getAllOrders();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public UserOrder createNewUserOrder(UserOrder userOrder){
		System.out.println(userOrder.toString());
		UserOrderDAO userOrderDAO = new UserOrderDAO();
		return userOrderDAO.createNewUserOrder(userOrder);
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/id")
	public UserOrder updateUserOrderById(@PathParam("{id}") int id,UserOrder userOrder){
		UserOrderDAO userOrderDAO = new UserOrderDAO();
		return userOrderDAO.updateUserOrderById(id, userOrder);
	}

}
