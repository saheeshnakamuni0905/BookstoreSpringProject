package com.project.store.DAO;

import com.project.store.POJO.Role;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDAO extends DAO{
	

    public Role findOrCreateRole(String roleName) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = DAO.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Query<Role> query = session.createQuery("FROM Role WHERE roleName = :roleName", Role.class);
            query.setParameter("roleName", roleName);
            Role role = query.uniqueResult();
            
            if (role == null) {
                role = new Role();
                role.setRoleName(roleName);
                session.persist(role);
            }

            transaction.commit();
            return role;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
