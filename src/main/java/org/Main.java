package org;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;
import org.db.hibernate.DB_Initializer_Demo;
import org.gui.fx.LoginPanel;

public class Main extends Application {
    @Getter
    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
        // road map finish concerts and fill database with data
        // split user list and profile into separate classes to make it more readable
        // repair pagination
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;

        LoginPanel loginPanel = new LoginPanel(primaryStage);
        loginPanel.showLoginPanel();
    }
}
