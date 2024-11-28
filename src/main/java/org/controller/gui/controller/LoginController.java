package org.controller.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.Main;
import org.controller.buisness.controller.UserLoginController;
import org.gui.fx.RegisterPanel;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    private final UserLoginController ULC = new UserLoginController();
    @FXML
    private Button exitButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    public void handleExitButtonAction() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Błąd", "Pola nie mogą być puste.");
            return;
        }
        boolean isAuthenticated = ULC.authenticateUser(username, password);
        if (isAuthenticated) {
            System.out.println("Pomyslnie zalogowano");
        } else {
            showAlert("Bład", "Niepoprawne dane logowania");
        }
    }


    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handleRegisterButtonAction() throws IOException {
        RegisterPanel registerPanel = new RegisterPanel(Main.getPrimaryStage());
        registerPanel.showRegisterPanel();
    }
}
