package com.project.store.DAO;

import com.project.store.POJO.Role;

import com.project.store.POJO.User;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class UserDAO extends DAO{
	
	private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    public User checkLogin(String username, String password) {
        Session session = null;
        try {
            session = DAO.getSessionFactory().openSession(); 
            logger.debug("Session opened for checkLogin user");
            Query<User> query = session.createQuery("FROM User WHERE username = :username AND password = :password", User.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            return query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close(); 
                logger.debug("Session closed for checklogin user");
            }
        }
    }


    public boolean createLogin(String username, String password, String email, String address, String phone, Date registrationDate, Integer roleId) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = DAO.getSessionFactory().openSession();
            logger.debug("Session opened for createlogin");
            transaction = session.beginTransaction();

            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password); 
            newUser.setEmail(email);
            newUser.setAddress(address);
            newUser.setPhone(phone);
            newUser.setRegistrationDate(registrationDate);
            
            Role role = session.get(Role.class, roleId); 
            newUser.setRole(role);

            session.persist(newUser);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close(); 
                logger.debug("Session closed for createlogin");
            }
        }
    }


    public void saveUser(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = DAO.getSessionFactory().openSession(); 
            logger.debug("Session opened for saveuser");
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
                logger.debug("Session closed for saveuser");
            }
        }
    }
    
    public void deleteUser(String username) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = DAO.getSessionFactory().openSession();
            logger.debug("Session opened for delete user");
            transaction = session.beginTransaction();
            Query<User> query = session.createQuery("FROM User WHERE username = :name", User.class);
            query.setParameter("name", username);
            User user = query.uniqueResult();
            if (user == null) {
                System.out.println("No author found with ID: " + username);
                return; 
            }
            System.out.println("DAO delete name " + user.getUsername());
            session.remove(user);
            
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
                logger.debug("Session closed for deleteuser");
            }
        }
    }
    

    public void updateUser(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = DAO.getSessionFactory().openSession(); 
            logger.debug("Session opened for updateuser");
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close(); 
                logger.debug("Session closed for updateuser");
            }
        }
    }



}
