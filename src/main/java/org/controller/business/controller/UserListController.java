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

public class UserListController {
    @FXML
    public Pagination pagination;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User,Integer> idColumn;
    @FXML
    private TableColumn<User,String>  firstNameColumn;
    @FXML
    private TableColumn<User,String>  lastNameColumn;
    @FXML
    private TableColumn<User,String>  emailColumn;

    @FXML
    private TableColumn<User,String>  birthDateColumn;
    private List<User> fullUserList;

    public void initialize(){
        System.out.println(emailColumn);
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        birthDateColumn.setCellValueFactory(cellData -> cellData.getValue().birthDateProperty().asString());

        loadUserList();
    }

    private void loadUserList(){
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            fullUserList = session.createQuery("from User", User.class).getResultList();
            int rowsPerPage = 20;
            int pageCount = (int) Math.ceil((double) fullUserList.size() / rowsPerPage);
            pagination.setPageCount(pageCount);

            loadPage(0);
            session.getTransaction().commit();
        }catch (HibernateException e){
            throw new RuntimeException("Błąd wczytywania listy użytkowników " + e.getMessage());
        }
    }
    private void loadPage(int pageIndex) {
        int rowsPerPage = 20;
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, fullUserList.size());

        ObservableList<User> pageData = FXCollections.observableArrayList(fullUserList.subList(fromIndex, toIndex));
        userTable.setItems(pageData);
    }
}
