package org.controller.business.controller;

import org.db.hibernate.*;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Timestamp;

public class TicketBuyService {
    public boolean buyTicket(Concert concert, int userID, String ticketType, String seatNumber) {
        if (concert.getAvailableTickets() <= 0) {
            return false;
        }

        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            Concert concertToUpdate = session.get(Concert.class, concert.getId(), LockMode.PESSIMISTIC_WRITE);
            if (concertToUpdate.getAvailableTickets() <= 0) {
                transaction.rollback();
                return false;
            }

            concertToUpdate.setAvailableTickets(concertToUpdate.getAvailableTickets() - 1);
            session.update(concertToUpdate);

            User usersTicket = session.get(User.class, userID);
            Ticket ticket = new Ticket();

            ticket.setConcert(concertToUpdate);
            ticket.setUser(usersTicket);
            ticket.setPrice(concertToUpdate.getTicketPrice());
            ticket.setPurchaseDate(new Timestamp(System.currentTimeMillis()));

            try {
                ticket.setTicketType(TicketType.valueOf(ticketType.toUpperCase()));
            } catch (IllegalArgumentException e) {
                transaction.rollback();
                throw new RuntimeException("Nieprawidłowy typ biletu: " + e.getMessage());
            }

            if (TicketType.SEATED.name().equals(ticketType.toUpperCase())) {
                if (seatNumber == null || seatNumber.isEmpty()) {
                    transaction.rollback();
                    throw new RuntimeException("Numer siedzenia jest wymagany dla biletu siedzącego.");
                }
                ticket.setSeatNumber(seatNumber);
            }

            session.save(ticket);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            throw new RuntimeException("Bład zakupu biletu: " + e.getMessage());
        }
    }
}
