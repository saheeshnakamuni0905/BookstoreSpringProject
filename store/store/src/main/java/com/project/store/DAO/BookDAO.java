package com.project.store.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import com.project.store.POJO.Book;


@Repository
public class BookDAO extends DAO{
	
	public void saveBook(Book book) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = DAO.getSessionFactory().openSession();  
            transaction = session.beginTransaction();
            session.persist(book);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();  
            }
        }
    }
	
	public List<Book> findAllBooks() {
        try (Session session = DAO.getSessionFactory().openSession()) {
            return session.createQuery("FROM Book", Book.class).list();
        }
    }
}
    


