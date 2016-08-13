package org.mudit.rath.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.mudit.rath.configuration.Role;
import org.mudit.rath.dao.MenuItemDAO;
import org.mudit.rath.model.MenuItem;
import org.mudit.rath.security.Secured;

@Path("/menuitem")
public class MenuItemResource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<MenuItem> getAllMenuItem(@Context ContainerRequestContext requestContext){
		MenuItemDAO menuItemDAO = new MenuItemDAO();
		return menuItemDAO.getAllMenuItem();
	}
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<MenuItem> getMenuItemByCategory(@PathParam("id") long id,@Context ContainerRequestContext requestContext){
		MenuItemDAO menuItemDAO = new MenuItemDAO();
		return menuItemDAO.getMenuItemByCategory(id);
	}
	@POST
	@Secured({Role.ADMIN,Role.STAFF})
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MenuItem createNewMenuItem(MenuItem menuitem,@Context ContainerRequestContext requestContext){
		MenuItemDAO menuItemDAO = new MenuItemDAO();
		System.out.println(menuitem.toString());
		return menuItemDAO.createNewMenuItem(menuitem);
	}
	
	@PUT
	@Secured({Role.ADMIN,Role.STAFF})
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/id")
	public MenuItem updateMenuItemById(@PathParam("{id}") int id,MenuItem menuItem,@Context ContainerRequestContext requestContext){
		MenuItemDAO menuItemDAO = new MenuItemDAO();
		return menuItemDAO.updateMenuItemById(id,menuItem);
	}
	
	@DELETE
	@Secured({Role.ADMIN,Role.STAFF})
	@Path("/id")
	public String deleteMenuItemById(@PathParam("{id") int id,@Context ContainerRequestContext requestContext){
		MenuItemDAO menuItemDAO = new MenuItemDAO();
		return menuItemDAO.deleteMenuItemById(id);
	}
}
