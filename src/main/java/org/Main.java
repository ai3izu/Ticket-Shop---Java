package org;

import javafx.application.Application;
import javafx.stage.Stage;
import org.db.hibernate.User;
import org.gui.fx.LoginPanel;

import java.util.Date;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginPanel loginPanel = new LoginPanel(primaryStage);
        loginPanel.showLoginPanel();
    }

    public static void main(String[] args) {
        launch(args);

//        User userTest = new User();
//        userTest.setFirstName("Jan");
//        userTest.setLastName("Kowalski");
//        userTest.setBirthDate(new Date());
//        userTest.setEmail("email@test.com");
//        userTest.setPassword("password");
//        userTest.setRole("user");
//
//        userTest.saveUserToDB(userTest);
        User user = new User();

        user.printUserDataFromDB(1);
    }
}
