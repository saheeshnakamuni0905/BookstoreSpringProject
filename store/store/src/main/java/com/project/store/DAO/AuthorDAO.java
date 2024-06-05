package com.project.store.DAO;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import com.project.store.POJO.Author;


@Repository
public class AuthorDAO extends DAO {
    
    public Author getAuthorById(Integer authorId) {
    	Session session = null;
        try {
        	session = DAO.getSessionFactory().openSession();
            return session.get(Author.class, authorId);
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close(); 
            }
        }
    }

    public void saveAuthor(Author author) {
        Transaction transaction = null;
        try (Session session = DAO.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(author);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (transaction != null) {
                transaction.markRollbackOnly();
            }
        }
    }
    
    public void updateAuthor(Author author) {
        Transaction transaction = null;
        try (Session session = DAO.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(author);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (transaction != null) {
                transaction.markRollbackOnly();
            }
        }
    }
    
    public void deleteAuthor(String authorName) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = DAO.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Query<Author> query = session.createQuery("FROM Author WHERE authorName = :name", Author.class);
            query.setParameter("name", authorName);
            Author author = query.uniqueResult();

            if (author == null) {
                System.out.println("No author found with ID: " + authorName);
                return; 
            }

            System.out.println("DAO delete name " + author.getAuthorName());
            session.remove(author);
            
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

}
