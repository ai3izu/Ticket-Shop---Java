package org.db.hibernate;

import javafx.beans.property.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "role", nullable = false)
    private String role;
    @Column(name = "password", nullable = false)
    private String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Ticket> tickets;

    public User() {
        this.role = "user";
    }

    public IntegerProperty idProperty() {
        return new SimpleIntegerProperty(id);
    }

    public StringProperty firstNameProperty() {
        return new SimpleStringProperty(firstName);
    }

    public StringProperty lastNameProperty() {
        return new SimpleStringProperty(lastName);
    }

    public StringProperty emailProperty() {
        return new SimpleStringProperty(email);
    }

    public ObjectProperty<Date> birthDateProperty() {
        return new SimpleObjectProperty<>(birthDate);
    }
}
