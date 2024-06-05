package com.project.store.DAO;

import org.hibernate.Session;

import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.project.store.POJO.*;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public class OrderDAO extends DAO {
	
    public void placeOrder(User user, List<CartItem> cartItems) {
    	System.out.println("Processing 1 " + cartItems.size());
        Transaction tx = null;
        try (Session session = DAO.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Order order = new Order();
            order.setUser(user);
            order.setOrderDate(new Date());
            order.setStatus("Pending");
            System.out.println("Session is open 0 : " + session.isOpen());
            System.out.println("Transaction is active 0: " + tx.isActive());

            BigDecimal totalAmount = BigDecimal.ZERO;
            System.out.println("Processing " + cartItems.size());
            
            for (CartItem cartItem : cartItems) {
            	System.out.println("Session is open 1: " + session.isOpen());
            	System.out.println("Transaction is active 1: " + tx.isActive());

            	System.out.println("Item: " + cartItem.getBook().getTitle() + ", Quantity: " + cartItem.getQuantity());
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setBook(cartItem.getBook());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPrice(cartItem.getBook().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
                session.persist(orderItem);
                totalAmount = totalAmount.add(orderItem.getPrice());
            }
            System.out.println("hi in ORder DAO");
            order.setTotalAmount(totalAmount);
            System.out.println("Session is open: " + session.isOpen());
            System.out.println("Transaction is active: " + tx.isActive());

            System.out.println("hi");
            session.persist(order);
            System.out.println("yoooo");
            session.flush(); 
            System.out.println("hi in 2r");
            for (CartItem cartItem : cartItems) {
                session.remove(cartItem);
            }
            System.out.println("hi in order");
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace(); 
            throw new RuntimeException(e);  
        } 
    }
}
