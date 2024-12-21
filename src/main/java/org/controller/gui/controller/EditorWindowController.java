package org.controller.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controller.business.controller.EditorWindowService;
import org.db.hibernate.Concert;
import org.gui.fx.NotificationAlert;

import java.time.LocalDate;

public class EditorWindowController {

    public TextField editorSearchBandField;
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
    private ListView<String> bandListView;
    private ObservableList<String> bandNames = FXCollections.observableArrayList();

    private EditorWindowService EWS;
    private final NotificationAlert ALERT = new NotificationAlert();

    public void initializeWithConcert(Concert concert) {
        this.EWS = new EditorWindowService(concert);

        bandListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        concertNameField.setText(concert.getName());
        concertTimeField.setText(concert.getTime().toString().substring(0, 5));
        concertTicketPriceField.setText(String.valueOf(concert.getTicketPrice()));
        concertAvailableTicketsField.setText(String.valueOf(concert.getAvailableTickets()));
        concertDateField.setValue(concert.getDate().toLocalDate());

        loadBandsFromDB(concert);
        editorSearchBandField.textProperty().addListener((observable, oldValue, newValue) -> filterBands(newValue));
    }

    private void loadBandsFromDB(Concert concert) {
         bandNames = EWS.loadBandsFromDB();
        bandListView.setItems(bandNames);

        ObservableList<String> assignedBandNames = EWS.getAssignedBandNames(concert);
        for (String bandName : assignedBandNames) {
            bandListView.getSelectionModel().select(bandName);
        }
    }

    @FXML
    public void handleSaveChangesButton() {
        String concertName = concertNameField.getText();
        String concertTime = concertTimeField.getText();
        String concertTicketPrice = concertTicketPriceField.getText();
        String concertAvailableTickets = concertAvailableTicketsField.getText();
        LocalDate concertDate = concertDateField.getValue();

        if (concertName.isEmpty() || concertTime.isEmpty() || concertTicketPrice.isEmpty() || concertAvailableTickets.isEmpty() || concertDate == null) {
            ALERT.showAlert("Błąd", "Wszystkie pola muszą być wypełnione.");
            return;
        }

        double ticketPrice;
        int availableTickets;
        try {
            ticketPrice = Double.parseDouble(concertTicketPrice);
            availableTickets = Integer.parseInt(concertAvailableTickets);
        } catch (NumberFormatException e) {
            ALERT.showAlert("Błąd", "- Cena biletu musi zawierać liczbę\n- Dostępne bilety muszą zawierać liczbę.");
            return;
        }

        ObservableList<String> selectedBands = bandListView.getSelectionModel().getSelectedItems();
        EWS.saveConcertChanges(concertName, concertTime, ticketPrice, availableTickets, concertDate, selectedBands);

        Stage stage = (Stage) concertNameField.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleCancelButton() {
        Stage stage = (Stage) concertNameField.getScene().getWindow();
        stage.close();
    }

    private void filterBands(String query) {
        if (query.isEmpty()) {
            bandListView.setItems(bandNames);
            return;
        }
        ObservableList<String> filteredBands = FXCollections.observableArrayList();
        for (String bandName : bandNames) {
            if (bandName.toLowerCase().contains(query.toLowerCase())) {
                filteredBands.add(bandName);
            }
        }
        bandListView.setItems(filteredBands);
    }
}
