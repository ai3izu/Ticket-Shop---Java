package org.db.hibernate;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;

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


    public void saveUserToDB(User user) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public void printUserDataFromDB(int userID){
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            User user =  session.get(User.class, userID);

            if (user !=null){
                System.out.println(user.getId());
                System.out.println(user.getFirstName());
                System.out.println(user.getLastName());
            } else System.out.println("user not found");
            session.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
            if (session.getTransaction() != null) session.getTransaction().rollback();
        } finally {
            session.close();
        }


    }

}
