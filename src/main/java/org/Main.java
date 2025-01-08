package org;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;
import org.db.hibernate.DB_Initializer;
import org.gui.fx.LoginPanel;

public class Main extends Application {
    @Getter
    private static Stage primaryStage;

    public static void main(String[] args) {
        if (!DB_Initializer.isDBInitialized()) {
            DB_Initializer.adminInitDB();
            DB_Initializer.bandInitDB();
            DB_Initializer.markAsInitialized();
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;

        LoginPanel loginPanel = new LoginPanel(primaryStage);
        loginPanel.showLoginPanel();
    }
}
