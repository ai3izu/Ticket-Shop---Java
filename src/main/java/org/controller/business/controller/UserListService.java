package org.controller.business.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.db.hibernate.HibernateUtil;
import org.db.hibernate.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public class UserListService {
    private final int rowsPerPage = 50;
    private List<User> fullUserList;

    public List<User> loadUserList() {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            fullUserList = session.createQuery("from User", User.class).getResultList();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new RuntimeException("Błąd wczytywania listy użytkowników: " + e.getMessage());
        }
        return fullUserList;
    }

    public int getPageCount(int totalItems) {
        return (int) Math.ceil((double) totalItems / rowsPerPage);
    }

    public List<User> getPageData(int pageIndex) {
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, fullUserList.size());
        return fullUserList.subList(fromIndex, toIndex);
    }
}
