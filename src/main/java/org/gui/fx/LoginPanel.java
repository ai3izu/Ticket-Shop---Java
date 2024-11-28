package org.gui.fx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
public class LoginPanel {
    @NonNull
    private Stage stage;

    public void showLoginPanel() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/LoginPanel.fxml")));
        Scene scene = stage.getScene();
        if (scene == null) {
            stage.setScene(new Scene(root));
        } else {
            scene.setRoot(root);
        }
        stage.setTitle("Login - Sleep Ticket");
        stage.show();
    }
}
