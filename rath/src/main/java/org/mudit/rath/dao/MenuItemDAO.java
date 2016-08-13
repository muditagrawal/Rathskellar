package org.mudit.rath.dao;

import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.mudit.rath.configuration.MySessionFactory;
import org.mudit.rath.model.Category;
import org.mudit.rath.model.MenuItem;

public class MenuItemDAO {
	public List<MenuItem> getAllMenuItem() {
		Session mSession = MySessionFactory.getMy_factory().openSession();
		mSession.beginTransaction();
		List<MenuItem> menuItems = (List<MenuItem>)mSession.createCriteria(MenuItem.class).list();
		System.out.println(menuItems);
		mSession.close();
		return menuItems;
	}
	
	public MenuItem createNewMenuItem(MenuItem menuItem){
		System.out.println(menuItem.getCategory().toString());
		Session mSession = MySessionFactory.getMy_factory().openSession();
		mSession.beginTransaction();
		MenuItem nMenuItem = new MenuItem();
		nMenuItem.setName(menuItem.getName());
		nMenuItem.setDescription(menuItem.getDescription());
		Category nCategory = mSession.get(Category.class, menuItem.getCategory().getId());
		nMenuItem.setCategory(nCategory);
		mSession.save(nMenuItem);
		mSession.getTransaction().commit();
		menuItem = (MenuItem)mSession.createCriteria(MenuItem.class).add(Restrictions.eq("name", menuItem.getName())).uniqueResult();
		mSession.close();
		return menuItem;
	}
	
	public MenuItem updateMenuItemById(int id,MenuItem menuItem) {
		menuItem.setId(id);
		Session mSession = MySessionFactory.getMy_factory().openSession();
		mSession.beginTransaction();
		mSession.update(menuItem);
		mSession.getTransaction().commit();
		mSession.close();
		return menuItem;
	}
	
	public String deleteMenuItemById(int id) {
		Session mSession = MySessionFactory.getMy_factory().openSession();
		mSession.beginTransaction();
		MenuItem menuItem = (MenuItem)mSession.get(MenuItem.class, id);
		mSession.delete(menuItem);
		mSession.getTransaction().commit();
		mSession.close();
		return "Menu Item Deleted";
		
	}

	public List<MenuItem> getMenuItemByCategory(long id) {
		// TODO Auto-generated method stub
		Session mSession = MySessionFactory.getMy_factory().openSession();
		mSession.beginTransaction();
		Category currentcategory = (Category)mSession.get(Category.class, id);
		List<MenuItem> menuItems = (List<MenuItem>)mSession.createCriteria(MenuItem.class).add(Restrictions.eq("category",currentcategory)).list();
		System.out.println(menuItems.size());
		mSession.close();
		return menuItems;
	}
}
