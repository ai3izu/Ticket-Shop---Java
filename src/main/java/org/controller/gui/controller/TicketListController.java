package org.controller.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.controller.business.controller.TicketListService;
import org.db.hibernate.Ticket;

import java.util.List;

public class TicketListController {

    private final TicketListService TLS = new TicketListService();
    private final ObservableList<Ticket> filteredTickets = FXCollections.observableArrayList();
    @FXML
    private TextField searchBar;
    @FXML
    private Pagination pagination;
    @FXML
    private TableView<Ticket> ticketTable;
    @FXML
    private TableColumn<Ticket, Integer> ticketIdColumn;
    @FXML
    private TableColumn<Ticket, String> concertNameColumn;
    @FXML
    private TableColumn<Ticket, String> firstNameColumn;
    @FXML
    private TableColumn<Ticket, String> lastNameColumn;
    @FXML
    private TableColumn<Ticket, String> emailColumn;
    @FXML
    private TableColumn<Ticket,String> purchasedDateColumn;

    public void initialize() {
        ticketIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        concertNameColumn.setCellValueFactory(cellData -> cellData.getValue().getConcert().nameProperty());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getUser().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getUser().lastNameProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().getUser().emailProperty());
        purchasedDateColumn.setCellValueFactory(cellData -> cellData.getValue().purchaseDateProperty().asString());

        loadTicketList();

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> filterTickets(newValue));

        pagination.setPageFactory(pageIndex -> {
            loadPage(pageIndex);
            return ticketTable;
        });
    }

    private void loadTicketList() {
        List<Ticket> ticketList = TLS.loadTicketList();
        filteredTickets.setAll(ticketList);

        int pageCount = TLS.getPageCount(ticketList.size());
        pagination.setPageCount(pageCount);
        loadPage(0);
    }

    private void loadPage(int pageIndex) {
        List<Ticket> pageData = TLS.getPageData(pageIndex);
        ObservableList<Ticket> observableList = FXCollections.observableArrayList(pageData);
        ticketTable.setItems(observableList);
    }

    private void filterTickets(String query) {
        List<Ticket> filtered = TLS.filterTickets(filteredTickets, query);
        ticketTable.setItems(FXCollections.observableArrayList(filtered));
    }
}

