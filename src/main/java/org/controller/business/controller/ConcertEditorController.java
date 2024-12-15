package org.controller.business.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.db.hibernate.Band;
import org.db.hibernate.Concert;
import org.db.hibernate.HibernateUtil;
import org.gui.fx.NotificationAlert;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

public class ConcertEditorController {

    private final NotificationAlert ALERT = new NotificationAlert();
    @FXML
    private Button saveConcertButton;
    @FXML
    private TextField concertNameField;
    @FXML
    private TextField concertTimeField;
    @FXML
    private TextField concertTicketPriceField;
    @FXML
    private TextField concertAvailableTicketsField;
    @FXML
    private DatePicker concertDateField;

    @FXML
    private ListView<String> concertEditListView;
    @FXML
    private ListView<String> bandListView;

    public void initialize() {
        bandListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        loadBandsFromDB();
        loadConcertsFromDB();
    }

    private void loadBandsFromDB() {
        try (Session session = HibernateUtil.getSession()) {
            Query<Band> query = session.createQuery("FROM Band", Band.class);
            List<Band> bands = query.list();

            ObservableList<String> bandNames = FXCollections.observableArrayList();
            for (Band band : bands) {
                bandNames.add(band.getName());
            }
            bandListView.setItems(bandNames);
        } catch (Exception e) {
            System.out.println("Błąd pozyskiwania zespołów z bazy: " + e.getMessage());
        }
    }

    private void loadConcertsFromDB() {
        try (Session session = HibernateUtil.getSession()) {
            Query<Concert> query = session.createQuery("FROM Concert", Concert.class);
            List<Concert> concerts = query.list();

            ObservableList<String> concertNames = FXCollections.observableArrayList();
            for (Concert concert : concerts) {
                concertNames.add(concert.getName());
            }
            concertEditListView.setItems(concertNames);
        } catch (Exception e) {
            System.out.println("Błąd pozyskiwania koncertów z bazy: " + e.getMessage());
        }
    }

    public void handleSaveConcertButton() {
        String concertName = concertNameField.getText();
        String concertTime = concertTimeField.getText();
        String concertTicketPrice = concertTicketPriceField.getText();
        String concertAvailableTickets = concertAvailableTicketsField.getText();
        LocalDate concertDate = concertDateField.getValue();

        if (concertName.isEmpty() || concertTime.isEmpty() || concertTicketPrice.isEmpty() || concertAvailableTickets.isEmpty() || concertDate == null) {
            ALERT.showAlert("Bład", "Wszystkie pola muszą być wypełnione.");
            return;
        }


        double ticketPrice = 0;
        int availableTickets = 0;
        try {
            ticketPrice = Double.parseDouble(concertTicketPrice);
            availableTickets = Integer.parseInt(concertAvailableTickets);
        } catch (NumberFormatException e) {
            ALERT.showAlert("Bład", "- Cena biletu musi zawierać liczbę \n - Dostępne bilety muszą zawierać liczbę.");
            return;
        }


        Concert concert = new Concert();
        concert.setName(concertName);
        concert.setTime(Time.valueOf(concertTime + ":00"));
        concert.setTicketPrice(ticketPrice);
        concert.setAvailableTickets(availableTickets);
        concert.setDate(Date.valueOf(concertDate));
        ObservableList<String> selectedBands = bandListView.getSelectionModel().getSelectedItems();
        if (selectedBands.isEmpty()) {
            ALERT.showAlert("Bład", "Wybierz przynajmniej jeden zespół.");
            return;
        }


        try (Session session = HibernateUtil.getSession()) {
            for (String bandName : selectedBands) {
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
            ALERT.showAlert("Sukces", "Koncert został zapisany.");
            loadConcertsFromDB();
            clearForm();
        } catch (Exception e) {
            System.out.println("Bład podczas zapisywania koncertu: " + e.getMessage());
        }

    }

    private void clearForm() {
        concertNameField.clear();
        concertTimeField.clear();
        concertTicketPriceField.clear();
        concertAvailableTicketsField.clear();
        concertDateField.setValue(null);
        bandListView.getSelectionModel().clearSelection();
    }
}


