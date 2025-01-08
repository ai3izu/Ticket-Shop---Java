package org.db.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Date;

public class DB_Initializer {
    private final static String[] bandNames = {"Sleep Token", "Death", "Lamb Of God", "Iron Maiden", "Pantera", "Slayer", "Metallica", "Burzum", "Mayhem", "Slipknot", "Michael Jackson", "One Direction", "Taylor Swift", "U2", "Red Hot Chili Peppers", "Korn"};

    public static void adminInitDB() {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();

            User admin = new User();
            admin.setFirstName("Admin");
            admin.setLastName("Admin");
            admin.setEmail("admin1@wp.pl");
            admin.setBirthDate(Date.valueOf("2000-01-01"));
            admin.setPassword(BCrypt.hashpw("Adminek1@", BCrypt.gensalt()));
            admin.setRole("admin");

            session.save(admin);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Błąd wczytywania bazy danych: " + e.getMessage());
        }
    }

    public static void bandInitDB() {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            for (String bandName : bandNames) {
                Band band = new Band();
                band.setName(bandName);
                session.save(band);
            }
            session.getTransaction().commit();
        }
    }

    public static boolean isDBInitialized() {
        try (Session session = HibernateUtil.getSession()) {
            InitializerStatus status = session.get(InitializerStatus.class, 1);
            return status != null && status.isInitialized();
        } catch (HibernateException e) {
            return false;
        }
    }

    public static void markAsInitialized() {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();

            InitializerStatus status = new InitializerStatus();
            status.setId(1);
            status.setInitialized(true);

            session.saveOrUpdate(status);
            session.getTransaction().commit();
        }catch (HibernateException e) {
            throw  new RuntimeException("Błąd inicjalizacji bazy danych: " + e.getMessage());
        }
    }
}
