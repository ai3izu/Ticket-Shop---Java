package org.controller.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.controller.business.controller.UserListService;
import org.db.hibernate.User;

import java.util.List;

public class UserListController {
    private final UserListService ULS = new UserListService();
    @FXML
    public Pagination pagination;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, Integer> idColumn;
    @FXML
    private TableColumn<User, String> firstNameColumn;
    @FXML
    private TableColumn<User, String> lastNameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, String> birthDateColumn;

    public void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        birthDateColumn.setCellValueFactory(cellData -> cellData.getValue().birthDateProperty().asString());

        loadUserList();

        pagination.setPageFactory(pageIndex -> {
            loadPage(pageIndex);
            return userTable;
        });
    }

    private void loadUserList() {
        List<User> userList = ULS.loadUserList();
        int pageCount = ULS.getPageCount(userList.size());

        pagination.setPageCount(pageCount);
        loadPage(0);
    }

    private void loadPage(int pageIndex) {
        List<User> pageData = ULS.getPageData(pageIndex);
        ObservableList<User> observableList = FXCollections.observableArrayList(pageData);
        userTable.setItems(observableList);
    }
}
