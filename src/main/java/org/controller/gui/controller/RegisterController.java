package org.controller.gui.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.Main;
import org.gui.fx.LoginPanel;

import java.util.Objects;

public class RegisterController {
    public void handleBackButtonAction() throws Exception {
        LoginPanel loginPanel = new LoginPanel(Main.getPrimaryStage());
        loginPanel.showLoginPanel();
    }

    public void handleRegisterButtonAction() {
        System.out.println("To be written..");
    }
}

