package org.controller.business.controller;

import org.db.hibernate.HibernateUtil;
import org.db.hibernate.Ticket;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;
import java.util.stream.Collectors;

public class TicketListService {
    private final int rowsPerPage = 50;
    private List<Ticket> fullTicketList;

    public List<Ticket> loadTicketList() {
        try (Session session = HibernateUtil.getSession()){
            session.beginTransaction();
            fullTicketList = session.createQuery("from Ticket", Ticket.class).getResultList();
            session.getTransaction().commit();
        }catch (HibernateException e){
            throw new RuntimeException("Bład wczytywania listy biletów: " + e.getMessage());
        }
        return fullTicketList;
    }

    public int getPageCount(int totalItems) {return (int) Math.ceil((double) totalItems / rowsPerPage);}

    public List<Ticket> getPageData(int pageIndex) {
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, fullTicketList.size());
        return fullTicketList.subList(fromIndex, toIndex);
    }

    public List<Ticket> filterTickets(List<Ticket> tickets, String query) {
        if (query == null || query.isEmpty()) {
            return tickets;
        }

        String lowerCaseQuery = query.toLowerCase();
        return tickets.stream()
                .filter(ticket -> ticket.getUser().getFirstName().toLowerCase().contains(lowerCaseQuery) ||
                        ticket.getUser().getEmail().toLowerCase().contains(lowerCaseQuery))
                .collect(Collectors.toList());
    }
}
