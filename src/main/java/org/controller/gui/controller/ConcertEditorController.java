package org.controller.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controller.business.controller.ConcertEditorService;
import org.db.hibernate.Concert;
import org.gui.fx.NotificationAlert;

import java.io.IOException;
import java.time.LocalDate;

public class ConcertEditorController {

    private final NotificationAlert ALERT = new NotificationAlert();
    private final ConcertEditorService CS = new ConcertEditorService();
    private final ObservableList<String> allBandNames = FXCollections.observableArrayList();
    private final ObservableList<Concert> allConcerts = FXCollections.observableArrayList();

    @FXML
    public TextField searchBandField;
    @FXML
    public TextField searchConcertField;
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
    private ListView<Concert> concertEditListView;
    @FXML
    private ListView<String> bandListView;

    public void initialize() {
        bandListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        loadBandsFromDB();
        loadConcertsFromDB();
        setupSearchbars();

        concertEditListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Concert selectedConcert = concertEditListView.getSelectionModel().getSelectedItem();
                if (selectedConcert != null) {
                    openConcertEditorWindow(selectedConcert);
                }
            }
        });
    }

    private void loadBandsFromDB() {
        allBandNames.setAll(CS.loadBandNamesFromDB());
        bandListView.setItems(allBandNames);
    }

    public void loadConcertsFromDB() {
        allConcerts.setAll(CS.loadConcertsFromDB());
        concertEditListView.setCellFactory(new Callback<ListView<Concert>, ListCell<Concert>>() {
            @Override
            public ListCell<Concert> call(ListView<Concert> param) {
                return new ListCell<Concert>() {
                    @Override
                    protected void updateItem(Concert concert, boolean empty) {
                        super.updateItem(concert, empty);
                        if (empty || concert == null) {
                            setText(null);
                        } else {
                            setText(concert.getName());
                        }
                    }
                };
            }
        });
        concertEditListView.setItems(allConcerts);
    }

    public void handleSaveConcertButton() {
        String concertName = concertNameField.getText();
        String concertTime = concertTimeField.getText();
        String concertTicketPrice = concertTicketPriceField.getText();
        String concertAvailableTickets = concertAvailableTicketsField.getText();
        LocalDate concertDate = concertDateField.getValue();

        ObservableList<String> selectedBands = bandListView.getSelectionModel().getSelectedItems();
        if (selectedBands.isEmpty()) {
            ALERT.showAlert("Błąd", "Wybierz przynajmniej jeden zespół.");
            return;
        }

        boolean success = CS.saveConcert(concertName, concertTime, concertTicketPrice, concertAvailableTickets, concertDate, selectedBands);
        if (success) {
            ALERT.showAlert("Sukces", "Koncert został zapisany.");
            loadConcertsFromDB();
            clearForm();
        } else {
            ALERT.showAlert("Błąd", "Wystąpił błąd podczas zapisywania koncertu.");
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

    private void setupSearchbars() {
        searchBandField.textProperty().addListener((e) -> filterBands(searchBandField.getText()));
        searchConcertField.textProperty().addListener((e) -> filterConcerts(searchConcertField.getText()));
    }

    private void filterBands(String query) {
        if (query.isEmpty()) {
            bandListView.setItems(allBandNames);
            return;
        }
        ObservableList<String> filteredBands = FXCollections.observableArrayList();
        for (String bandName : allBandNames) {
            if (bandName.toLowerCase().contains(query.toLowerCase())) {
                filteredBands.add(bandName);
            }
        }
        bandListView.setItems(filteredBands);
    }

    private void filterConcerts(String query) {
        if (query.isEmpty()) {
            concertEditListView.setItems(allConcerts);
            return;
        }
        ObservableList<Concert> filteredConcerts = FXCollections.observableArrayList();
        for (Concert concert : allConcerts) {
            if (concert.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredConcerts.add(concert);
            }
        }
        concertEditListView.setItems(filteredConcerts);
    }

    private void openConcertEditorWindow(Concert concert) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainWindow/View/ConcertEditWindow.fxml"));
            Parent root = loader.load();

            EditorWindowController editorController = loader.getController();
            editorController.initializeWithConcert(concert);


            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Edytuj koncert");
            stage.setScene(scene);
            stage.setWidth(800);
            stage.setHeight(900);
            stage.setOnHidden(event -> loadConcertsFromDB());
            stage.show();
        } catch (IOException e) {
            ALERT.showAlert("Błąd", "Nie udało się otworzyć okna edycji koncertu.");
        }
    }
}

