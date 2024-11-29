package org.controller.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.Main;
import org.controller.buisness.controller.UserLoginController;
import org.gui.fx.NotificationAlert;
import org.gui.fx.RegisterPanel;

import java.io.IOException;

public class LoginController {
    private final UserLoginController ULC = new UserLoginController();
    private final NotificationAlert ALERT = new NotificationAlert();
    @FXML
    private Button exitButton;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    @FXML
    public void handleExitButtonAction() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void handleLoginButtonAction() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            ALERT.showAlert("Błąd", "Pola nie mogą być puste.");
            return;
        }
        if (isValidEmail(email)) {
            ALERT.showAlert("Błąd", "Niepoprawny format adresu e-mail");
        }
        boolean isAuthenticated = ULC.authenticateUser(email, password);
        if (isAuthenticated) {
            System.out.println("Pomyslnie zalogowano");
        } else {
            ALERT.showAlert("Bład", "Niepoprawne dane logowania");
        }
    }

    public void handleRegisterButtonAction() throws IOException {
        RegisterPanel registerPanel = new RegisterPanel(Main.getPrimaryStage());
        registerPanel.showRegisterPanel();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}
