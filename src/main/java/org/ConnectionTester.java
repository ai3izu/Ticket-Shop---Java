package org;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ConnectionTester {
    private static SessionFactory sessionFactory;
    public static boolean testConnection() {
        try {
            sessionFactory = new Configuration().configure("hibernate-config.xml").buildSessionFactory();
            sessionFactory.openSession().close();
            return true;
        } catch (HibernateException e) {
            return false;
        } finally {
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        }
    }
    public static void showConnectionError() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd połączenia");
            alert.setHeaderText("Nie można nawiązać połączenia z bazą danych.");
            alert.setContentText("Upewnij się, że:\n"
                    + "1. Apache jest uruchomiony w XAMPP.\n"
                    + "2. Serwer MySQL jest uruchomiony w XAMPP.\n"
                    + "3. Plik hibernate-config jest odpowiednio skonfigurowany.");
            alert.showAndWait();
            System.exit(1);
        });
    }
}
