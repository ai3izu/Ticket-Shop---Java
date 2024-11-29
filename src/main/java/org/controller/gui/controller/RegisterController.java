package org.controller.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.Main;
import org.controller.buisness.controller.UserRegisterController;
import org.db.hibernate.User;
import org.gui.fx.LoginPanel;
import org.gui.fx.NotificationAlert;

import java.sql.Date;
import java.time.LocalDate;
import java.util.stream.Stream;


public class RegisterController {
    private final UserRegisterController URC = new UserRegisterController();
    private final NotificationAlert ALERT = new NotificationAlert();
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;

    public void handleBackButtonAction() throws Exception {
        LoginPanel loginPanel = new LoginPanel(Main.getPrimaryStage());
        loginPanel.showLoginPanel();
    }

    public void handleRegisterButtonAction() throws Exception {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        LocalDate birthDate = birthDatePicker.getValue();
        String email = emailField.getText();
        String password = passwordField.getText();


        if (Stream.of(firstName, lastName, email, password).anyMatch(String::isEmpty) || birthDate == null) {
            ALERT.showAlert("Bład", "Wszystkie pola muszą być wypełnione.");
            return;
        }

        User registeredUser = new User();
        registeredUser.setFirstName(firstName);
        registeredUser.setLastName(lastName);
        registeredUser.setBirthDate(Date.valueOf(birthDate));
        registeredUser.setEmail(email);
        registeredUser.setPassword(password);


        boolean isRegistered = URC.registerUserToDB(registeredUser);
        if (isRegistered) {
            ALERT.showAlert("Sukces", "rejestracja przebiegla pomyslnie");
            LoginPanel loginPanel = new LoginPanel(Main.getPrimaryStage());
            loginPanel.showLoginPanel();
        } else ALERT.showAlert("Bład", "rejestracja nie powiodła się");
    }
}

