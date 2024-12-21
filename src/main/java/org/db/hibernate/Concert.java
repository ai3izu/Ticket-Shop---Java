package org.db.hibernate;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "concerts")
@Getter
@Setter
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "concert_bands",
            joinColumns = @JoinColumn(name = "concert_id"),
            inverseJoinColumns = @JoinColumn(name = "band_id")
    )
    private Set<Band> bands = new HashSet<>();

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "time", nullable = false)
    private Time time;

    @Column(name = "ticket_price", nullable = false)
    private double ticketPrice;

    @Column(name = "available_tickets", nullable = false)
    private int availableTickets;

    @OneToMany(mappedBy = "concert", cascade = CascadeType.ALL)
    private Set<Ticket> tickets;
}
