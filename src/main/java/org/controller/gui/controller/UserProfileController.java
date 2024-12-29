package org.controller.gui.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controller.business.controller.UserProfileService;
import org.db.hibernate.Ticket;
import org.db.hibernate.User;
import org.db.hibernate.UserSession;

import java.util.List;

public class UserProfileController {
    private final UserProfileService UPS = new UserProfileService();
    @FXML
    public Label welcomeLabel;
    @FXML
    public Label nameLabel;
    @FXML
    public Label emailLabel;
    @FXML
    public Label dateLabel;
    @FXML
    public Label roleLabel;
    public Label surnameLabel;
    @FXML
    private VBox ticketsContainer;


    public void initialize() {


        Integer userID = UserSession.getLoggedInUserId();
        if (userID != null) {
            User currentUser = UPS.retrieveUserDataFromDatabase(userID);
            if (currentUser != null) {
                System.out.println("Dane użytkownika pobrane");
                welcomeLabel.setText("Witaj " + currentUser.getFirstName() + "!");
                nameLabel.setText("Imię: " + currentUser.getFirstName());
                surnameLabel.setText("Nazwisko: " + currentUser.getLastName());
                emailLabel.setText("Adres email: " + currentUser.getEmail());
                dateLabel.setText("Data urodzenia: " + currentUser.getBirthDate());
                roleLabel.setText("Rola: " + currentUser.getRole());
            } else {
                System.out.println("Brak danych użytkownika");
            }
        }

        List<Ticket> userTickets = UPS.loadUserTicketsFromDB(userID);
        displayTicketCards(userTickets);
    }

    private void displayTicketCards(List<Ticket> ticketList) {
        for (Ticket ticket : ticketList) {
            Pane ticketCard = createTicketCard(ticket);
            ticketCard.setPrefWidth(300);
            ticketsContainer.getChildren().add(ticketCard);
        }
    }

    private Pane createTicketCard(Ticket ticket) {
        if (ticket == null || ticket.getConcert() == null) {
            System.out.println("Nieprawidłowy bilet lub brak koncertu.");
            return new Pane();
        }

        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: #1b2838; " + "-fx-padding: 20px; " + "-fx-border-radius: 10px; " + "-fx-background-radius: 10px; " + "-fx-alignment: center;");
        card.setAlignment(Pos.CENTER);
        Text concertName = new Text("Bilet: " + ticket.getConcert().getName());
        concertName.setStyle("-fx-fill: white; -fx-font-size: 18px;");

        Text seatInfo;
        if (ticket.getSeatNumber() != null) {
            seatInfo = new Text("Numer siedzenia: " + ticket.getSeatNumber());
        } else {
            seatInfo = new Text("Miejsce stojące");
        }

        seatInfo.setStyle("-fx-fill: white; -fx-font-size: 16px;");
        card.getChildren().addAll(concertName, seatInfo);
        card.setPrefWidth(300);
        return card;
    }
}