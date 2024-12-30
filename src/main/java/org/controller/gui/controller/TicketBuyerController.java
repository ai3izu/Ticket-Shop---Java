package org.controller.gui.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.controller.business.controller.AdminEditorService;
import org.controller.business.controller.TicketBuyerService;
import org.db.hibernate.Concert;
import org.db.hibernate.UserSession;
import org.gui.fx.NotificationAlert;

import java.util.List;
import java.util.Objects;

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
            HBox concertCard = createConcertCard(concert);
            concertsContainer.getChildren().add(concertCard);
        }
    }

    private HBox createConcertCard(Concert concert) {
        HBox concertCard = new HBox();
        concertCard.setSpacing(20);
        concertCard.setPadding(new Insets(20));
        concertCard.setStyle("-fx-background-color: #34495e; -fx-border-radius: 15; -fx-padding: 10px 20px; -fx-border-width: 1; -fx-border-color: #2c3e50;");

        VBox details = new VBox();
        details.setSpacing(10);
        details.setAlignment(Pos.TOP_LEFT);

        Label nameLabel = new Label(concert.getName());
        nameLabel.setStyle("-fx-font-size: 24px; -fx-font-family: 'Roboto'; -fx-text-fill: #ffffff;");

        Label priceLabel = new Label("Cena: " + concert.getTicketPrice() + " PLN");
        priceLabel.setStyle("-fx-font-size: 18px; -fx-font-family: 'Roboto'; -fx-text-fill: #ffffff;");

        details.getChildren().addAll(nameLabel, priceLabel);

        VBox ticketInfo = new VBox(10);
        ticketInfo.setAlignment(Pos.TOP_LEFT);
        ticketInfo.setSpacing(8);

        Label ticketsLabel = new Label("Dostępne bilety: " + concert.getAvailableTickets());
        ticketsLabel.setStyle("-fx-font-size: 18px; -fx-font-family: 'Roboto'; -fx-text-fill: #ffffff;");

        Label ticketTypeLabel = new Label("Typ biletu:");
        ticketTypeLabel.setStyle("-fx-font-size: 16px; -fx-font-family: 'Roboto'; -fx-text-fill: #ffffff;");

        ChoiceBox<String> ticketTypeChoiceBox = new ChoiceBox<>();
        ticketTypeChoiceBox.getItems().addAll("Stojący", "Siedzący");
        ticketTypeChoiceBox.setValue("Stojący");

        Label seatNumberLabel = new Label("Numer siedzenia:");
        seatNumberLabel.setStyle("-fx-font-size: 16px; -fx-font-family: 'Roboto'; -fx-text-fill: #ffffff;");

        TextField seatNumberField = new TextField();
        seatNumberField.setPromptText("np. A12");
        seatNumberField.setDisable(true);

        ticketTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> seatNumberField.setDisable(!"Siedzący".equals(newValue)));

        ticketInfo.getChildren().addAll(ticketsLabel, ticketTypeLabel, ticketTypeChoiceBox, seatNumberLabel, seatNumberField);

        Button buyButton = new Button("Kup Bilet");
        buyButton.setStyle("-fx-background-color: #4CAF50; -fx-border-radius: 10; -fx-text-fill: #ffffff; -fx-padding: 10px 20px; -fx-font-size: 18px;");

        ticketInfo.getChildren().add(buyButton);

        if (concert.getAvailableTickets() <= 0) {
            buyButton.setDisable(true);
            ticketsLabel.setText("Brak dostępnych biletów");
        } else {
            buyButton.setOnAction(event -> handleTicketPurchase(concert, ticketTypeChoiceBox.getValue(), seatNumberField.getText()));
        }

        VBox imageBox = new VBox();
        imageBox.setAlignment(Pos.CENTER_RIGHT);
        ImageView concertImage = new ImageView(Objects.requireNonNull(getClass().getResource("/images/logo-sleep-ticket.png")).toExternalForm());
        concertImage.setFitWidth(200);
        concertImage.setFitHeight(200);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Region spacer2 = new Region();
        spacer2.setPrefWidth(25);

        imageBox.getChildren().add(concertImage);

        concertCard.getChildren().addAll(details, spacer2, ticketInfo, spacer, imageBox);

        return concertCard;
    }

    private void handleTicketPurchase(Concert concert, String ticketType, String seatNumber) {
        String mappedTicketType;
        if ("Siedzący".equals(ticketType)) {
            mappedTicketType = "SEATED";
        } else if ("Stojący".equals(ticketType)) {
            mappedTicketType = "STANDING";
        } else {
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
