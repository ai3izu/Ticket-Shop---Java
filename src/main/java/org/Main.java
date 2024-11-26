package org;

import javafx.application.Application;
import javafx.stage.Stage;
import org.gui.fx.LoginPanel;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginPanel loginPanel = new LoginPanel(primaryStage);
        loginPanel.showLoginPanel();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
