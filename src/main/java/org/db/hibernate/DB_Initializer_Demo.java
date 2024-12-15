package org.db.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.mapping.Set;

import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;

public class DB_Initializer_Demo {
    public static void addBandsToDo(){
        Band band1 = new Band();
        Band band2 = new Band();
        Band band3 = new Band();
        Band band4 = new Band();
        Band band5 = new Band();
        Band band6 = new Band();
        Band band7 = new Band();
        Band band8 = new Band();
        Band band9 = new Band();
        Band band10 = new Band();
        Band band11 = new Band();
        Band band12 = new Band();
        Band band13 = new Band();
        Band band14 = new Band();
        Band band15 = new Band();
        Band band16 = new Band();
        Band band17 = new Band();
        Band band18 = new Band();
        Band band19 = new Band();
        Band band20 = new Band();
        Band band21 = new Band();
        Band band22 = new Band();
        Band band23 = new Band();
        Band band24 = new Band();
        Band band25 = new Band();

        band1.setName("Chelsea Grin");
        band2.setName("Sleep Token");
        band3.setName("Slpiknot");
        band4.setName("Shadow of Intent");
        band5.setName("Death");
        band6.setName("Cannibal Corpse");
        band7.setName("Slayer");
        band8.setName("Ice Nine Kills");
        band9.setName("Gojira");
        band10.setName("Michael Jackson");
        band11.setName("Miles Davis Quintet");
        band12.setName("John Coltrane Quartet");
        band13.setName("Bill Evans Trio");
        band14.setName("Dizzy Gillespie Big Band");
        band15.setName("Charlie Parker Quintet");
        band16.setName("Spice Girls");
        band17.setName("Backstreet Boys");
        band18.setName("One Direction");
        band19.setName("The Beatles");
        band20.setName("The Rolling Stones");
        band21.setName("Queen");
        band22.setName("Metallica");
        band23.setName("Led Zeppelin");
        band24.setName("Guns N Roses");
        band25.setName("Alice in Chains");
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(band1);
        session.save(band2);
        session.save(band3);
        session.save(band4);
        session.save(band5);
        session.save(band6);
        session.save(band7);
        session.save(band8);
        session.save(band9);
        session.save(band10);
        session.save(band11);
        session.save(band12);
        session.save(band13);
        session.save(band14);
        session.save(band15);
        session.save(band16);
        session.save(band17);
        session.save(band18);
        session.save(band19);
        session.save(band20);
        session.save(band21);
        session.save(band22);
        session.save(band23);
        session.save(band24);
        session.save(band25);
        transaction.commit();
    }

    public static void addConcertsToDo() {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        System.out.println("Starting transaction...");
        Band band1 = session.get(Band.class, 1);
        Band band2 = session.get(Band.class, 2);
        Band band3 = session.get(Band.class, 3);
        Band band4 = session.get(Band.class, 4);
        Band band5 = session.get(Band.class, 5);
        Band band6 = session.get(Band.class, 6);
        Band band7 = session.get(Band.class, 7);
        Band band8 = session.get(Band.class, 8);
        Band band9 = session.get(Band.class, 9);
        Band band10 = session.get(Band.class, 10);
        Band band11 = session.get(Band.class, 11);
        Band band12 = session.get(Band.class, 12);
        Band band13 = session.get(Band.class, 13);
        Band band14 = session.get(Band.class, 14);
        Band band15 = session.get(Band.class, 15);
        Band band16 = session.get(Band.class, 16);
        Band band17 = session.get(Band.class, 17);
        Band band18 = session.get(Band.class, 18);
        Band band19 = session.get(Band.class, 19);
        Band band20 = session.get(Band.class, 20);
        Band band21 = session.get(Band.class, 21);
        Band band22 = session.get(Band.class, 22);
        Band band23 = session.get(Band.class, 23);
        Band band24 = session.get(Band.class, 24);
        Band band25 = session.get(Band.class, 25);


//        Concert concert1 = new Concert();
//        concert1.setName("Metal Night");
//        concert1.setDate(Date.valueOf("2025-01-01"));
//        concert1.setTime(Time.valueOf("20:00:00"));
//        concert1.setTicketPrice(100);
//        concert1.setAvailableTickets(500);
//        concert1.getBands().add(band1);
//        concert1.getBands().add(band2);
//        concert1.getBands().add(band3);
//
//
//
//
//        Concert concert2 = new Concert();
//        concert2.setName("Rock Night");
//        concert2.setDate(Date.valueOf("2025-01-02"));
//        concert2.setTime(Time.valueOf("20:00:00"));
//        concert2.setTicketPrice(100);
//        concert2.setAvailableTickets(500);
//        concert1.getBands().add(band1);
//        concert1.getBands().add(band2);
//        concert1.getBands().add(band3);


        Concert concert3 = new Concert();
        concert3.setName("Worship Night 2025");
        concert3.setDate(Date.valueOf("2025-05-26"));
        concert3.setTime(Time.valueOf("23:00:00"));
        concert3.setTicketPrice(350);
        concert3.setAvailableTickets(100);
        concert3.getBands().add(band2);
        concert3.getBands().add(band8);


        Concert concert4 = new Concert();
        concert4.setName("Corpse Fest 2025");
        concert4.setDate(Date.valueOf("2025-11-09"));
        concert4.setTime(Time.valueOf("20:00:00"));
        concert4.setTicketPrice(235.5);
        concert4.setAvailableTickets(666);
        concert4.getBands().add(band6);
        concert4.getBands().add(band5);
        concert4.getBands().add(band4);


        Concert concert5 = new Concert();
        concert5.setName("Pop Show 2025");
        concert5.setDate(Date.valueOf("2025-04-13"));
        concert5.setTime(Time.valueOf("16:30:00"));
        concert5.setTicketPrice(50);
        concert5.setAvailableTickets(2000);
        concert5.getBands().add(band16);
        concert5.getBands().add(band18);
        concert5.getBands().add(band10);


        Concert concert6 = new Concert();
        Concert concert7 = new Concert();
        Concert concert8 = new Concert();
        Concert concert9 = new Concert();
        Concert concert10 = new Concert();



        session.save(concert3);
        session.save(concert4);
        session.save(concert5);
        transaction.commit();
    }
}
