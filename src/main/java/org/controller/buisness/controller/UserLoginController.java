package org.controller.buisness.controller;

import org.db.hibernate.HibernateUtil;
import org.db.hibernate.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class UserLoginController {
    public boolean authenticateUser(String username, String password) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            Query<User> query = session.createQuery("FROM User WHERE first_name = :first_name AND password = :password", User.class);
            query.setParameter("first_name", username);
            query.setParameter("password", password);

            User user = query.uniqueResult();
            session.getTransaction().commit();

            return user != null;
        } catch (Exception e) {
            if (session.getTransaction().isActive()) session.getTransaction().rollback();
            System.out.println("Error authenticating user " + e.getMessage());
            return false;
        } finally {
            session.close();
        }
    }
}
