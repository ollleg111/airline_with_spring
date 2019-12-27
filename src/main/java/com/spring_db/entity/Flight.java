package com.spring_db.entity;

import javax.persistence.*;

@Entity
@Table(name = "FLIHGT")
public class Flight {

    public Flight() {
    }

    private long id;

    @Id
    @SequenceGenerator(name = "PL_SEQ", sequenceName = "FLIGHT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PL_SEQ")
    @Column(name = "ID")
    public long getId() {
        return id;
    }
}
