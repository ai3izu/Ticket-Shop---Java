package org.controller.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.Main;
import org.controller.business.controller.UserRegisterService;
import org.db.hibernate.User;
import org.gui.fx.LoginPanel;
import org.gui.fx.NotificationAlert;

import java.sql.Date;
import java.time.LocalDate;
import java.util.regex.Pattern;
import java.util.stream.Stream;


public class RegisterController {
    private final UserRegisterService URS = new UserRegisterService();
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

        if (!validateRegisterData()) return;

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


        boolean isRegistered = URS.registerUserToDB(registeredUser);
        if (isRegistered) {
            ALERT.showAlert("Sukces", "rejestracja przebiegla pomyslnie");
            LoginPanel loginPanel = new LoginPanel(Main.getPrimaryStage());
            loginPanel.showLoginPanel();
        } else ALERT.showAlert("Bład", "rejestracja nie powiodła się");
    }

    private boolean validateRegisterData(){
        if (passwordField.getLength() < 8){
            ALERT.showAlert("Bład", "Hasło powinno zawierać conajmniej 8 znaków.");
            return false;
        }
        String regex = "^(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?=.*\\d).*$";
        if (!Pattern.matches(regex,passwordField.getText())){
            ALERT.showAlert("Błąd", "Hasło powinno zawierać:\n - co najmniej 1 znak specjalny,\n - co najmniej 1 wielką literę,\n - co najmniej 1 cyfrę.");
            return false;
        }
        if (emailField == null || !emailField.getText().contains("@")) {
            ALERT.showAlert("Błąd", "Adres e-mail musi zawierać znak '@'.");
            return false;
        }
        return true;
    }
}

