package org.gui.fx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Data;

import java.io.IOException;
import java.util.Objects;

public class LoginPanel {
    private Stage stage;
    public LoginPanel(Stage stage) {
        this.stage = stage;
    }

    public void showLoginPanel() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LoginPanel.fxml")));
        stage = new Stage();
        stage.setTitle("Login - Sleep Ticket");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
