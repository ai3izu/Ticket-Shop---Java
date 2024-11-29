package org.controller.buisness.controller;

import org.db.hibernate.HibernateUtil;
import org.db.hibernate.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;

public class UserLoginController {
    public boolean authenticateUser(String email, String password) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            Query<User> query = session.createQuery("FROM User WHERE email = :email", User.class);
            query.setParameter("email", email);

            User user = query.uniqueResult();
            session.getTransaction().commit();

            if (user != null) return BCrypt.checkpw(password, user.getPassword());
            return false;
        } catch (Exception e) {
            if (session.getTransaction().isActive()) session.getTransaction().rollback();
            System.out.println("Error authenticating user " + e.getMessage());
            return false;
        } finally {
            session.close();
        }
    }
}
