package org.controller.business.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.db.hibernate.HibernateUtil;
import org.db.hibernate.User;
import org.db.hibernate.UserSession;
import org.hibernate.Session;

public class UserProfileService {
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

    public void initialize() {
        Integer userID = UserSession.getLoggedInUserId();
        if (userID != null) {
            User currentUser = retrieveUserDataFromDatabase(userID);
            if (currentUser != null) {
                System.out.println("Dane użytkownika pobrane");
                welcomeLabel.setText("Witaj " + currentUser.getFirstName());
                nameLabel.setText("Imię: " + currentUser.getFirstName());
                surnameLabel.setText("Nazwisko: " + currentUser.getLastName());
                emailLabel.setText("Adres email: " + currentUser.getEmail());
                dateLabel.setText("Data urodzenia: " + currentUser.getBirthDate());
                roleLabel.setText("Rola: " + currentUser.getRole());
            } else {
                System.out.println("Brak danych użytkownika");
            }
        }
    }

    public User retrieveUserDataFromDatabase(Integer userID) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            User user = session.get(User.class, userID);
            session.getTransaction().commit();
            return user;
        } catch (Exception e) {
            System.out.println("Błąd pozyskiwania użytkownika z bazy" + e.getMessage());
            return null;
        }
    }
}
