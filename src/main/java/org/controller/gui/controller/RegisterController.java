package org.controller.gui.controller;

import org.Main;
import org.gui.fx.LoginPanel;


public class RegisterController {
    public void handleBackButtonAction() throws Exception {
        LoginPanel loginPanel = new LoginPanel(Main.getPrimaryStage());
        loginPanel.showLoginPanel();
    }

    public void handleRegisterButtonAction() {
        System.out.println("To be written..");
    }
}

