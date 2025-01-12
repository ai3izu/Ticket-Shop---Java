package org.controller.business.controller;

import org.db.hibernate.HibernateUtil;
import org.db.hibernate.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;

import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Klasa służąca do zarządzania procesem rejestracji użytkowników.
 * Oferuje metody do rejestrowania nowych użytkowników w bazie danych,
 * weryfikacji danych użytkownika oraz hasła.
 */
public class RegisterService {
    /**
     * Rejestruje nowego użytkownika w bazie danych.
     *
     * @param user Obiekt użytkownika, który ma zostać zarejestrowany.
     * @return true, jeśli rejestracja zakończyła się sukcesem; false w przeciwnym razie.
     */
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

    /**
     * Sprawdza, czy podany adres e-mail już istnieje w bazie danych.
     *
     * @param email Adres e-mail do weryfikacji.
     * @return true, jeśli adres e-mail istnieje; false w przeciwnym razie.
     */
    private boolean doesEmailAlreadyExist(String email) {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("From User where email = :email", User.class).setParameter("email", email).uniqueResult() != null;
        } catch (Exception e) {
            System.out.println("Błąd sprawdzania czy email istnieje: " + e.getMessage());
            return false;
        }
    }

    /**
     * Hashuje hasło użytkownika przy użyciu algorytmu BCrypt.
     *
     * @param password Hasło do zhashowania.
     * @return Zhashowane hasło.
     */
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Weryfikuje dane użytkownika, sprawdzając, czy pola są wypełnione
     * oraz czy hasło i e-mail są poprawne.
     *
     * @param user Obiekt użytkownika do weryfikacji.
     * @return true, jeśli dane są poprawne; false w przeciwnym razie.
     */
    private boolean validateUserData(User user) {
        return Stream.of(user.getFirstName(), user.getLastName()).allMatch(field -> field != null && !field.isEmpty()) && user.getBirthDate() != null && validatePassword(user.getPassword()) && validateEmail(user.getEmail());
    }

    /**
     * Weryfikuje, czy podane hasło spełnia wymagane kryteria.
     *
     * @param password Hasło do weryfikacji.
     * @return true, jeśli hasło jest poprawne; false w przeciwnym razie.
     */
    private boolean validatePassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        return Pattern.matches("^(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?=.*\\d).*$", password);
    }

    /**
     * Weryfikuje, czy podany adres e-mail jest poprawny.
     *
     * @param email Adres e-mail do weryfikacji.
     * @return true, jeśli adres e-mail jest poprawny; false w przeciwnym razie.
     */
    private boolean validateEmail(String email) {
        if (email == null || !email.contains("@")) {
            System.out.println("Błąd: Email musi zawierać '@'.");
            return false;
        }
        return true;
    }
}
