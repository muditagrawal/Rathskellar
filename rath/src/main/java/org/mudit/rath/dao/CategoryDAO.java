package org.mudit.rath.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.mudit.rath.configuration.MySessionFactory;
import org.mudit.rath.model.Category;

public class CategoryDAO {
	public List<Category> getCategories(){
		Session mSession = MySessionFactory.getMy_factory().openSession();
		mSession.beginTransaction();
		List<Category> allcategories = (List<Category>)mSession.createCriteria(Category.class).list();
		mSession.close();
		System.out.println(allcategories.size());
		return allcategories;
	}

	public Category addNewCategory(Category newCategory) {
		Category nCategory = new Category();
		nCategory.setName(newCategory.getName());
		nCategory.setDescription(newCategory.getDescription());
		Session nSession = MySessionFactory.getMy_factory().openSession();
		nSession.beginTransaction();
		nSession.save(nCategory);
		nSession.getTransaction().commit();
		System.out.println(nCategory.toString());
		newCategory = (Category)nSession.createCriteria(Category.class).add(Restrictions.eq("name", nCategory.getName())).uniqueResult();
		nSession.close();
		return newCategory;
	}
}
