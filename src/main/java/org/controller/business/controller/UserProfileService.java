package org.controller.business.controller;


import org.db.hibernate.HibernateUtil;
import org.db.hibernate.Ticket;
import org.db.hibernate.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public class UserProfileService {
    public User retrieveUserDataFromDatabase(Integer userID) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            User user = session.get(User.class, userID);
            session.getTransaction().commit();
            return user;
        } catch (HibernateException e) {
            throw new RuntimeException("Błąd pozyskiwania danych użytkownika z bazy: " + e.getMessage());
        }
    }

    public List<Ticket> loadUserTicketsFromDB(Integer userID) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            List<Ticket> ticketList = session.createQuery("FROM Ticket WHERE user_id = :userID", Ticket.class)
                    .setParameter("userID", userID)
                    .getResultList();
            session.getTransaction().commit();
            return ticketList;
        } catch (HibernateException e) {
            throw new RuntimeException("Błąd pozyskiwania biletów z bazy: " + e.getMessage());
        }
    }
}
