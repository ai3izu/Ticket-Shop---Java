package org.gui.fx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.controller.gui.controller.MainWindowController;
import org.db.hibernate.User;

import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
public class AppMainWindow {
    @NonNull
    private Stage stage;

    public void showMainAppWindow(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/AppMainWindow.fxml")));
        Parent root = loader.load();

        MainWindowController controller = loader.getController();
        controller.setUserView(user);

        Scene scene = stage.getScene();
        if (scene == null) {
            stage.setScene(new Scene(root));
        } else {
            scene.setRoot(root);
        }
        stage.setTitle("Sleep Ticket");
        stage.setWidth(1600);
        stage.setHeight(900);
        stage.centerOnScreen();
        stage.show();
    }
}
