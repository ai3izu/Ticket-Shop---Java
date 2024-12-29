package org.controller.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.Main;
import org.controller.business.controller.MiscService;
import org.controller.business.controller.RegisterService;
import org.db.hibernate.Concert;
import org.db.hibernate.User;
import org.gui.fx.LoginPanel;

import java.io.IOException;
import java.util.List;

public class MainWindowController {
    private final MiscService MS = new MiscService();
    @FXML
    private VBox popularConcertsVBox;
    @FXML
    private Label availableConcertsLabel;
    @FXML
    private Label registeredUsersLabel;
    @FXML
    private Button concertEditorButton;
    @FXML
    private Button boughtTicketsButton;
    @FXML
    private Button userListButton;
    @FXML
    private Button exitButton;
    @FXML
    private BorderPane borderPane;
    private User user;

    public void setUserView(User user) {
        this.user = user;
        updateUserVisibility();
    }

    public void initialize() {
        updateDashboard();
    }


    private void updateUserVisibility() {

        userListButton.setVisible(user != null && "admin".equals(user.getRole()));
        concertEditorButton.setVisible(user != null && "admin".equals(user.getRole()));
    }

    public void handleExitButton() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleLogoutButton() throws IOException {
        LoginPanel loginPanel = new LoginPanel(Main.getPrimaryStage());
        loginPanel.showLoginPanel();

        Stage stage = Main.getPrimaryStage();
        stage.setWidth(600);
        stage.setHeight(900);
        stage.centerOnScreen();
    }

    @FXML
    public void handleMainPageButton() throws IOException {
        loadView("MainPage.fxml");
    }

    public void handleProfileButton() throws IOException {
        loadView("Profile.fxml");
    }


    @FXML
    public void handleUserListButton() throws IOException {
        loadView("UserList.fxml");
    }

    @FXML
    public void handleConcertEditorButton() throws IOException {
        loadView("AdminEditor.fxml");
    }

    @FXML
    public void handleTicketBuyButton() throws IOException {
        loadView("ConcertTickets.fxml");
    }

    private void loadView(String fileName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainWindow/View/" + fileName));
        Pane view = loader.load();
        borderPane.setCenter(view);
    }

    private void updateDashboard() {
        int registeredUsers = MS.getRegisteredUsersCount();
        registeredUsersLabel.setText("Liczba zarejestrowanych użytkowników: " + registeredUsers);

        int availableConcerts = MS.getConcertsCount();
        availableConcertsLabel.setText("Liczba dostępnych koncertów: " + availableConcerts);

        List<Concert> popularConcerts = MS.popularConcerts(3);
        popularConcertsVBox.getChildren().clear();
        for (Concert popularConcert : popularConcerts) {
            Label popularConcertLabel = new Label(popularConcert.getName());
            popularConcertLabel.setStyle("-fx-font-size: 22px; -fx-font-family: 'Roboto'; -fx-text-fill: WHITE;");
            popularConcertsVBox.getChildren().add(popularConcertLabel);
        }
    }
}
