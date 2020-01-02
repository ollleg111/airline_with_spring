package com.spring_db.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/*
CREATE TABLE FLIGHT(
ID NUMBER PRIMARY KEY,
PLANE NUMBER,
CONSTRAINT PLANE_FK FOREIGN KEY (PLANE) REFERENCES PLANE(ID),
DATE_FLIGHT DATE NOT NULL,
CITY_FROM NVARCHAR2(50) NOT NULL,
CITY_TO NVARCHAR2(50) NOT NULL
);
*/

@Entity
@Table(name = "FLIGHT")
public class Flight {

    private long id;
    private Plane plane;
    private Date dateFlight;
    private String cityFrom;
    private String cityTo;
    private List<Passenger> passengers;

    public Flight() {
    }

    @Id
    @SequenceGenerator(name = "FLIGHT_SEQ", sequenceName = "FLIGHT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FLIGHT_SEQ")
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PLANE")
    public Plane getPlane() {
        return plane;
    }

    @Column(name = "DATE_FLIGHT")
    public Date getDateFlight() {
        return dateFlight;
    }

    @Column(name = "CITY_FROM")
    public String getCityFrom() {
        return cityFrom;
    }

    @Column(name = "CITY_TO")
    public String getCityTo() {
        return cityTo;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "FLIGHTS_PASSENGERS",
            joinColumns = @JoinColumn(name = "FLIGHT_ID"),
            inverseJoinColumns = @JoinColumn(name = "PASSENGER_ID"))
    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public void setDateFlight(Date dateFlight) {
        this.dateFlight = dateFlight;
    }

    public void setCityFrom(String cityFrom) {
        this.cityFrom = cityFrom;
    }

    public void setCityTo(String cityTo) {
        this.cityTo = cityTo;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", plane=" + plane +
                ", dateFlight=" + dateFlight +
                ", cityFrom='" + cityFrom + '\'' +
                ", cityTo='" + cityTo + '\'' +
                ", passengers=" + passengers +
                '}';
    }
}
