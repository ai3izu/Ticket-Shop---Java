package org;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import org.gui.fx.LoginPanel;

import java.util.Objects;


public class Main extends Application {
    @Getter
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;

        LoginPanel loginPanel = new LoginPanel(primaryStage);
        loginPanel.showLoginPanel();

    }

    public static void main(String[] args) {
        launch(args);
//
//        User userTest = new User();
//        userTest.setFirstName("Jan");
//        userTest.setLastName("Kowalski");
//        userTest.setBirthDate(new Date());
//        userTest.setEmail("email@test.com");
//        userTest.setPassword("password");
//        userTest.setRole("user");
//
//        userTest.saveUserToDB(userTest);

    }
}
