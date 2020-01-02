package com.spring_db.entity;

import javax.persistence.*;
import java.util.Date;

/*
CREATE TABLE PLANE(
ID NUMBER PRIMARY KEY,
MODEL NVARCHAR2(50) NOT NULL,
CODE NVARCHAR2(50) NOT NULL,
YEAR_PRODUCED DATE NOT NULL,
AVG_FUEL_CONSUMPTION NUMBER (5,2)
);
*/

@Entity
@Table(name = "PLANE")
public class Plane {

    private long id;
    private String model;
    private String code;
    private Date yearProduction;
    private Double avgFuelConsumption;

    public Plane() {
    }

    @Id
    @SequenceGenerator(name = "PLANE_SEQ", sequenceName = "PLANE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLANE_SEQ")
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    @Column(name = "MODEL")
    public String getModel() {
        return model;
    }

    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    @Column(name = "YEAR_PRODUCED")
    public Date getYearProduction() {
        return yearProduction;
    }

    @Column(name = "AVG_FUEL_CONSUMPTION")
    public Double getAvgFuelConsumption() {
        return avgFuelConsumption;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setYearProduction(Date yearProduction) {
        this.yearProduction = yearProduction;
    }

    public void setAvgFuelConsumption(Double avgFuelConsumption) {
        this.avgFuelConsumption = avgFuelConsumption;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", code='" + code + '\'' +
                ", yearProduction=" + yearProduction +
                ", avgFuelConsumption=" + avgFuelConsumption +
                '}';
    }
}
