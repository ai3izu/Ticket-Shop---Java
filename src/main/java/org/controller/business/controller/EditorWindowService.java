package org.controller.business.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Setter;
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

@Setter
public class EditorWindowService {

    private final Concert currentConcert;

    public EditorWindowService(Concert currentConcert) {
        this.currentConcert = currentConcert;
    }

    public ObservableList<String> loadBandsFromDB() {
        ObservableList<String> bandNames = FXCollections.observableArrayList();
        try (Session session = HibernateUtil.getSession()) {
            Query<Band> query = session.createQuery("FROM Band", Band.class);
            List<Band> bands = query.list();
            for (Band band : bands) {
                bandNames.add(band.getName());
            }
        } catch (HibernateException e) {
            throw new RuntimeException("Błąd pozyskiwania zespołów z bazy: " + e.getMessage());
        }
        return bandNames;
    }

    public ObservableList<String> getAssignedBandNames(Concert concert) {
        ObservableList<String> bandNames = FXCollections.observableArrayList();
        concert.getBands().forEach(band -> bandNames.add(band.getName()));
        return bandNames;
    }

    public void saveConcertChanges(String concertName, String concertTime, double ticketPrice, int availableTickets, LocalDate concertDate, ObservableList<String> selectedBands) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();

            currentConcert.setName(concertName);
            currentConcert.setTime(Time.valueOf(concertTime + ":00"));
            currentConcert.setTicketPrice(ticketPrice);
            currentConcert.setAvailableTickets(availableTickets);
            currentConcert.setDate(Date.valueOf(concertDate));

            for (String bandName : selectedBands) {
                boolean alreadyAssigned = currentConcert.getBands().stream()
                        .anyMatch(band -> band.getName().equals(bandName));
                if (!alreadyAssigned) {
                    Query<Band> bandQuery = session.createQuery("FROM Band WHERE name = :name", Band.class);
                    bandQuery.setParameter("name", bandName);
                    Band band = bandQuery.uniqueResult();
                    if (band != null) {
                        currentConcert.getBands().add(band);
                    }
                }
            }

            session.update(currentConcert);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new RuntimeException("Błąd zapisywania danych koncertu: " + e.getMessage());
        }
    }
}
