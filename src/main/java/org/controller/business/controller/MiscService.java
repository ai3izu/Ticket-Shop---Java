package org.controller.business.controller;

import org.db.hibernate.Concert;
import org.db.hibernate.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;

public class MiscService {
    public int getRegisteredUsersCount() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return ((Long) session.createQuery("SELECT COUNT(u) FROM User u").uniqueResult()).intValue();
        } catch (HibernateException e) {
            throw new RuntimeException("Błąd pobierania liczby użytkowników: " + e.getMessage());
        }
    }

    public int getConcertsCount() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return ((Long) session.createQuery("SELECT COUNT(c) FROM Concert c").uniqueResult()).intValue();
        } catch (HibernateException e) {
            throw new RuntimeException("Błąd pobierania liczby koncertów: " + e.getMessage());
        }
    }

    public List<Concert> popularConcerts(int limit) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Concert> query = session.createQuery("FROM Concert", Concert.class);
            List<Concert> concerts = query.list();
            return pickRandomConcert(concerts, limit);
        } catch (HibernateException e) {
            throw new RuntimeException("Błąd pobierania popularnych koncertów: " + e.getMessage());
        }
    }

    private List<Concert> pickRandomConcert(List<Concert> concerts, int limit) {
        Collections.shuffle(concerts);
        return concerts.subList(0, Math.min(limit, concerts.size()));
    }
}
