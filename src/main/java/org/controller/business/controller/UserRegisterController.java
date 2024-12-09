package org.controller.business.controller;

import org.db.hibernate.HibernateUtil;
import org.db.hibernate.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;

import java.util.regex.Pattern;
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
            return true;
        } catch (Exception e) {
            System.out.println("Błąd podczas uwierzytelniania użytkownika " + e.getMessage());
            return false;
        }
    }

    private boolean doesEmailAlreadyExist(String email) {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("From User where email = :email", User.class).setParameter("email", email).uniqueResult() != null;
        } catch (Exception e) {
            System.out.println("Błąd sprawdzania czy email istnieje: " + e.getMessage());
            return false;
        }
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private boolean validateUserData(User user) {
        return Stream.of(user.getFirstName(), user.getLastName(), user.getEmail()).allMatch(field -> field != null && !field.isEmpty()) && user.getBirthDate() != null && validatePassword(user.getPassword());
    }

    private boolean validatePassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        return Pattern.matches("^(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?=.*\\d).*$", password);
    }
    private boolean validateEmail(String email) {
        if (email == null || !email.contains("@")) {
            System.out.println("Błąd: Email musi zawierać '@'.");
            return false;
        }
        return true;

    }
}
