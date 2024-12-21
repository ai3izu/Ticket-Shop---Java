package org;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;
import org.gui.fx.LoginPanel;

public class Main extends Application {
    @Getter
    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
        // update exception handlers
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;

        LoginPanel loginPanel = new LoginPanel(primaryStage);
        loginPanel.showLoginPanel();
    }
}
