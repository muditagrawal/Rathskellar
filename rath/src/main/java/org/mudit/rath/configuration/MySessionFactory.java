package org.mudit.rath.configuration;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class MySessionFactory {
	private static SessionFactory my_factory;
	
	private MySessionFactory(){
		my_factory = new Configuration().configure().buildSessionFactory();
	}

	public static SessionFactory getMy_factory() {
		if (my_factory == null)
			my_factory = new Configuration().configure().buildSessionFactory();
		return my_factory;
	}

	public static void setMy_factory(SessionFactory my_factory) {
		MySessionFactory.my_factory = my_factory;
	}
	
}
