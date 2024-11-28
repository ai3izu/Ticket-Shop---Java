package org.db.hibernate;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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

}
