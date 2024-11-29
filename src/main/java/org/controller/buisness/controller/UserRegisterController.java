package org.controller.buisness.controller;

import org.db.hibernate.HibernateUtil;
import org.db.hibernate.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;

import java.util.stream.Stream;

public class UserRegisterController {
    public boolean registerUserToDB(User user) {
        if (!validateUserData(user)) {
            return false;
        }
        if (doesEmailAlreadyExist(user.getEmail())) {
            return false;
        }

        user.setPassword(hashPassword(user.getPassword()));

        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            System.out.println("dodane do bazy");
            return true;
        } catch (Exception e) {
            System.out.println("Error authenticating user " + e.getMessage());
            return false;
        }
    }

    private boolean doesEmailAlreadyExist(String email) {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery(
                    "From User where email = :email", User.class
            ).setParameter("email", email).uniqueResult() != null;
        } catch (Exception e) {
            System.out.println("Error checking email existence: " + e.getMessage());
            return false;
        }
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private boolean validateUserData(User user) {
        return Stream.of(
                user.getFirstName(), user.getLastName(), user.getEmail()
        ).allMatch(field -> field != null && !field.isEmpty()) && user.getBirthDate() != null;
    }
}
