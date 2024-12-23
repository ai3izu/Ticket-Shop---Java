package org.controller.business.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.db.hibernate.Band;
import org.db.hibernate.Concert;
import org.db.hibernate.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

public class AdminEditorService {

    public ObservableList<String> loadBandNamesFromDB() {
        ObservableList<String> bandNames = FXCollections.observableArrayList();
        try (Session session = HibernateUtil.getSession()) {
            Query<Band> query = session.createQuery("FROM Band", Band.class);
            List<Band> bands = query.list();
            for (Band band : bands) {
                bandNames.add(band.getName());
            }
        } catch (HibernateException e) {
            throw new RuntimeException("Błąd pozyskiwania zespołów z bazy: " + e.getMessage());
        }
        return bandNames;
    }

    public ObservableList<Concert> loadConcertsFromDB() {
        ObservableList<Concert> concerts = FXCollections.observableArrayList();
        try (Session session = HibernateUtil.getSession()) {
            Query<Concert> query = session.createQuery("FROM Concert", Concert.class);
            List<Concert> concertList = query.list();
            concerts.addAll(concertList);
        } catch (HibernateException e) {
            throw new RuntimeException("Błąd pozyskiwania koncertów z bazy: " + e.getMessage());
        }
        return concerts;
    }

    public boolean saveConcert(String concertName, String concertTime, String concertTicketPrice, String concertAvailableTickets, LocalDate concertDate, List<String> selectedBandNames) {
        if (concertName.isEmpty() || concertTime.isEmpty() || concertTicketPrice.isEmpty() || concertAvailableTickets.isEmpty() || concertDate == null) {
            return false;
        }

        double ticketPrice;
        int availableTickets;
        try {
            ticketPrice = Double.parseDouble(concertTicketPrice);
            availableTickets = Integer.parseInt(concertAvailableTickets);
        } catch (NumberFormatException e) {
            return false;
        }

        Concert concert = new Concert();
        concert.setName(concertName);
        concert.setTime(Time.valueOf(concertTime + ":00"));
        concert.setTicketPrice(ticketPrice);
        concert.setAvailableTickets(availableTickets);
        concert.setDate(Date.valueOf(concertDate));

        try (Session session = HibernateUtil.getSession()) {
            for (String bandName : selectedBandNames) {
                Query<Band> bandQuery = session.createQuery("FROM Band WHERE name = :name", Band.class);
                bandQuery.setParameter("name", bandName);
                Band band = bandQuery.uniqueResult();
                if (band != null) {
                    concert.getBands().add(band);
                }
            }

            session.beginTransaction();
            session.save(concert);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            throw new RuntimeException("Błąd podczas zapisywania koncertu: " + e.getMessage());
        }
    }
}
