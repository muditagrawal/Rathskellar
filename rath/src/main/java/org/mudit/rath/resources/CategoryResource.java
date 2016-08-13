package org.mudit.rath.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.mudit.rath.dao.CategoryDAO;
import org.mudit.rath.model.Category;

@Path("/category")
public class CategoryResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Category> getCategories(){
		CategoryDAO mCategoryDAO = new CategoryDAO();
		return mCategoryDAO.getCategories();
	}
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Category addNewCategory(Category newCategory){
		CategoryDAO mCategoryDAO = new CategoryDAO();
		return mCategoryDAO.addNewCategory(newCategory);
	}
}
