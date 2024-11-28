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
public class RegisterPanel {
    @NonNull
    private Stage stage;

    public void showRegisterPanel() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/RegisterPanel.fxml")));
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(root);
            stage.setScene(scene);
        } else {
            scene.setRoot(root);
        }
        stage.setTitle("Register - Sleep Ticket");
        stage.show();
    }
}
