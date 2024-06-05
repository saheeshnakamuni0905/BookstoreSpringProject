package com.project.store.DAO;

import org.hibernate.SessionFactory;

import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.cfg.Environment;
import org.springframework.stereotype.Component;

import com.project.store.POJO.Author;
import com.project.store.POJO.Book;
import com.project.store.POJO.Cart;
import com.project.store.POJO.CartItem;
import com.project.store.POJO.Order;
import com.project.store.POJO.OrderItem;
import com.project.store.POJO.Role;
import com.project.store.POJO.User;

@Component
public class DAO {
	
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if(sessionFactory  == null) {
			try {
				StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySetting(Environment.DRIVER, "com.mysql.cj.jdbc.Driver")
						.applySetting(Environment.URL, "jdbc:mysql://localhost:3306/dbname")
						.applySetting(Environment.USER, "*****")
						.applySetting(Environment.PASS, "***************")
						.applySetting(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect")
						.applySetting(Environment.SHOW_SQL, "true")
						.applySetting(Environment.HBM2DDL_AUTO,"validate")
						.build();
				MetadataSources meta = new MetadataSources(serviceRegistry);
				meta.addAnnotatedClass(User.class);
				meta.addAnnotatedClass(Role.class);
				meta.addAnnotatedClass(Book.class);
				meta.addAnnotatedClass(Author.class);
				meta.addAnnotatedClass(Cart.class);
				meta.addAnnotatedClass(CartItem.class);
				meta.addAnnotatedClass(Order.class);
				meta.addAnnotatedClass(OrderItem.class);
				Metadata metadata = meta.getMetadataBuilder().build();
	            sessionFactory = metadata.getSessionFactoryBuilder().build();
			}catch (Exception e) {
	            e.printStackTrace(); }
		}
		return sessionFactory;
	}
}
