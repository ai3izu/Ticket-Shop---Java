package org.gui.fx;

import javafx.scene.control.Alert;

public class NotificationAlert {
    private final Alert ALERT = new Alert(Alert.AlertType.INFORMATION);

    public void showAlert(String title, String message){
        ALERT.setTitle(title);
        ALERT.setHeaderText(null);
        ALERT.setContentText(message);
        ALERT.showAndWait();
    }
}
