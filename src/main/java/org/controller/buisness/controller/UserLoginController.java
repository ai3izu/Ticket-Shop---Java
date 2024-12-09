package org.controller.buisness.controller;

import org.db.hibernate.HibernateUtil;
import org.db.hibernate.User;
import org.db.hibernate.UserSession;
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

            if (user != null && BCrypt.checkpw(password, user.getPassword())) {
                UserSession.setLoggedInUserId(user.getId());
                return true;
            }
            return false;
        } catch (Exception e) {
            if (session.getTransaction().isActive()) session.getTransaction().rollback();
            System.out.println("Error authenticating user " + e.getMessage());
            return false;
        } finally {
            session.close();
        }
    }

    public User getLoggedInUser() {
        Integer userId = UserSession.getLoggedInUserId();
        if (userId == null) return null;

        try (Session session = HibernateUtil.getSession()) {
            return session.get(User.class, userId);
        } catch (Exception e) {
            System.out.println("Error fetching logged in user: " + e.getMessage());
            return null;
        }
    }
}
