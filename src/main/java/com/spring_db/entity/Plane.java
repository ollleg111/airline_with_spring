package com.spring_db.entity;

import javax.persistence.*;

@Entity
@Table(name = "PLANE")
public class Plane {

    public Plane() {
    }

    private long id;

    @Id
    @SequenceGenerator(name = "PL_SEQ", sequenceName = "PLANE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PL_SEQ")
    @Column(name = "ID")
    public long getId() {
        return id;
    }
}
