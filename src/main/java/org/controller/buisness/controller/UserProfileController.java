package org.controller.buisness.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import org.db.hibernate.HibernateUtil;
import org.db.hibernate.User;
import org.db.hibernate.UserSession;
import org.hibernate.Session;

public class UserProfileController {
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
    public Text userNameText;

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
                System.out.println("No user data available");
            }
        }
    }

    private User retrieveUserDataFromDatabase(Integer userID) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        User user = session.get(User.class, userID);
        session.getTransaction().commit();
        return user;
    }
}
