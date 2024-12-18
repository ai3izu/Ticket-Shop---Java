package org.controller.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.Main;
import org.db.hibernate.User;
import org.gui.fx.LoginPanel;

import java.io.IOException;

public class AppMainWindowController {
    public Button concertEditorButton;
    @FXML
    private Button userListButton;
    @FXML
    private Button exitButton;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Button mainPageButton;
    private User user;

    public void setUserView(User user) {
        this.user = user;
        updateUserVisibility();
    }

    private void updateUserVisibility() {

        userListButton.setVisible(user != null && "admin".equals(user.getRole()));
        concertEditorButton.setVisible(user != null && "admin".equals(user.getRole()));
    }

    @FXML
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

    public void handleMainPageButton() throws IOException {
        loadView("MainPage.fxml");
    }

    public void handleProfileButton() throws IOException {
       loadView("Profile.fxml");
    }

    public void handleUserListButton() throws IOException {
        loadView("UserList.fxml");
    }
    private void loadView(String fileName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainWindow/View/" + fileName));
        Pane view = loader.load();
        borderPane.setCenter(view);
    }

    public void handleConcertEditorButton() throws IOException {
        loadView("ConcertEditor.fxml");
    }
}
