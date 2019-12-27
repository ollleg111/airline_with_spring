package com.spring_db.entity;

import javax.persistence.*;

@Entity
@Table(name = "PASSENGER")
public class Passenger {

    public Passenger() {
    }

    private long id;

    @Id
    @SequenceGenerator(name = "PS_SEQ", sequenceName = "PASSENGER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PS_SEQ")
    @Column(name = "ID")
    public long getId() {
        return id;
    }
}
