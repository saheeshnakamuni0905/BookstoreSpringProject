package com.project.store.DAO;

import org.hibernate.HibernateException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.project.store.POJO.Book;
import com.project.store.POJO.Cart;
import com.project.store.POJO.CartItem;
import com.project.store.POJO.User;

@Repository
public class CartDAO extends DAO{
	
	public Cart getCartByUserId(Integer userId) {
		System.out.println(userId);
	    Session session = null;
	    try {
	        session = DAO.getSessionFactory().openSession();
	        User user = session.get(User.class, userId);
	        if (user != null) {
	            return user.getCart();
	        } else {
	            return null;
	        }
	    } catch (HibernateException e) {
	        e.printStackTrace();
	        return null;
	    } finally {
	        if (session != null) {
	            session.close(); 
	        }
	    }
	}


	public void addToCart(int userId, int bookId, int quantity) {
        try (Session session = DAO.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            
            Cart cart = findOrCreateCart(session, userId);
            Book book = session.get(Book.class, bookId);

            CartItem item = new CartItem();
            item.setCart(cart);
            item.setBook(book);
            item.setQuantity(quantity);

            session.persist(item);
            cart.getCartItems().add(item); 
            System.out.println("cart dao items n"+ cart.getCartItems().size());
            tx.commit();
            session.refresh(cart);
        }
    }
    
    public Cart findOrCreateCart(Session session, int userId) {
        Query<Cart> query = session.createQuery("FROM Cart WHERE user.Id = :userId", Cart.class);
       System.out.println("CARTDAO"+ userId);
        query.setParameter("userId", userId);
        Cart cart = query.uniqueResult();
        
        if (cart == null) {
            cart = new Cart();
            User user = session.get(User.class, userId);
            cart.setUser(user);
            session.persist(cart);
        }
        return cart;
    }
    
    public void deleteCartItem(int cartItemId) {
        try (Session session = DAO.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            CartItem cartItem = session.get(CartItem.class, cartItemId);
            session.remove(cartItem);

            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

}
