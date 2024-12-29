package org.controller.gui.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controller.business.controller.AdminEditorService;
import org.controller.business.controller.TicketBuyerService;
import org.db.hibernate.Concert;
import org.db.hibernate.UserSession;
import org.gui.fx.NotificationAlert;

import java.util.List;

public class TicketBuyerController {

    private final AdminEditorService CES = new AdminEditorService();
    private final TicketBuyerService TBS = new TicketBuyerService();
    private final NotificationAlert ALERT = new NotificationAlert();
    @FXML
    private TextField searchBar;
    @FXML
    private VBox concertsContainer;


    private List<Concert> availableConcerts;

    public void initialize() {
        List<Concert> concerts = CES.loadConcertsFromDB();
        availableConcerts = concerts;
        loadCards(concerts);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> setupSearchBar(newValue));
    }

    private void setupSearchBar(String query) {
        if (query.isEmpty()) {
            concertsContainer.getChildren().clear();
            loadCards(availableConcerts);
            return;
        }
        ObservableList<Concert> filteredConcerts = FXCollections.observableArrayList();
        for (Concert concert : availableConcerts) {
            if (concert.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredConcerts.add(concert);
            }
        }
        concertsContainer.getChildren().clear();
        loadCards(filteredConcerts);
    }

    private void loadCards(List<Concert> concerts) {
        for (Concert concert : concerts) {
            VBox concertCard = createConcertCard(concert);
            concertsContainer.getChildren().add(concertCard);
        }
    }

    private VBox createConcertCard(Concert concert) {
        VBox concertCard = new VBox();
        concertCard.setSpacing(10);
        concertCard.setStyle("-fx-background-color: #34495e; -fx-border-radius: 15; -fx-padding: 10px 20px;");

        HBox topRow = new HBox(20);
        topRow.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        Label nameLabel = new Label(concert.getName());
        nameLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #ffffff;");

        Label ticketTypeLabel = new Label("Typ biletu:");
        ticketTypeLabel.setStyle("-fx-text-fill: #ffffff;");
        ChoiceBox<String> ticketTypeChoiceBox = new ChoiceBox<>();
        ticketTypeChoiceBox.getItems().addAll("Stojący", "Siedzący");
        ticketTypeChoiceBox.setValue("Stojący");

        topRow.getChildren().addAll(nameLabel, ticketTypeLabel, ticketTypeChoiceBox);

        HBox middleRow = new HBox(20);
        middleRow.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        Label ticketsLabel = new Label("Dostępne bilety: " + concert.getAvailableTickets());
        ticketsLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #ffffff;");

        Label seatNumberLabel = new Label("Numer siedzenia:");
        seatNumberLabel.setStyle("-fx-text-fill: #ffffff;");

        TextField seatNumberField = new TextField();
        seatNumberField.setPromptText("np. A12");
        seatNumberField.setDisable(true);

        ticketTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> seatNumberField.setDisable(!"Siedzący".equals(newValue)));

        middleRow.getChildren().addAll(ticketsLabel, seatNumberLabel, seatNumberField);

        HBox priceRow = new HBox();
        priceRow.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        Label priceLabel = new Label("Cena: " + concert.getTicketPrice() + " PLN");
        priceLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #ffffff;");

        priceRow.getChildren().add(priceLabel);

        Button buyButton = new Button("Kup Bilet");
        buyButton.setStyle("-fx-background-color: #2980b9; -fx-border-radius: 10; -fx-text-fill: #ffffff; -fx-padding: 10px 20px;");

        if (concert.getAvailableTickets() <= 0) {
            buyButton.setDisable(true);
            ticketsLabel.setText("Brak dostępnych biletów");
        } else {
            buyButton.setOnAction(event -> handleTicketPurchase(concert, ticketsLabel, ticketTypeChoiceBox.getValue(), seatNumberField.getText()));
        }

        concertCard.setAlignment(Pos.CENTER_LEFT);
        concertCard.getChildren().addAll(topRow, middleRow, priceRow, buyButton);

        return concertCard;
    }

    private void handleTicketPurchase(Concert concert, Label ticketsLabel, String ticketType, String seatNumber) {
        String mappedTicketType;
        if ("Siedzący".equals(ticketType)) {
            mappedTicketType = "SEATED";
        } else if ("Stojący".equals(ticketType)) {
            mappedTicketType = "STANDING";
        } else {
            ALERT.showAlert("Błąd", "Nieprawidłowy typ biletu.");
            return;
        }
        if ("SEATED".equals(mappedTicketType) && (seatNumber == null || seatNumber.trim().isEmpty())) {
            ALERT.showAlert("Błąd", "Numer siedzenia jest wymagany.");
            return;
        }
        boolean isPurchased = TBS.buyTicket(concert, UserSession.getLoggedInUserId(), mappedTicketType, seatNumber);
        if (isPurchased) {
            ALERT.showAlert("Sukces", "Bilet został zakupiony.");
            Platform.runLater(() -> {
                List<Concert> concerts = CES.loadConcertsFromDB();
                concertsContainer.getChildren().clear();
                loadCards(concerts);
            });
        } else {
            ALERT.showAlert("Błąd", "Nie udalo sie kupic biletu.");
        }
    }
}
