package org.controller.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.Main;
import org.gui.fx.LoginPanel;

import java.io.IOException;

public class AppMainWindowController {
    @FXML
    private Button exitButton;
    @FXML
    private Button logoutButton;

    @FXML
    public void handleExitButton() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void handleLogoutButton() throws IOException {
        LoginPanel loginPanel = new LoginPanel(Main.getPrimaryStage());
        loginPanel.showLoginPanel();


        Stage stage = Main.getPrimaryStage();
        stage.setWidth(600);
        stage.setHeight(900);
        stage.centerOnScreen();
    }
}
