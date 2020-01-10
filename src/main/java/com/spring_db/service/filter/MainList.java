package com.spring_db.service.filter;

import com.spring_db.entity.Flight;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MainList {

    private List<Criteria> list;

    public MainList(List<Criteria> list) {
        this.list = list;
    }

    public List<Criteria> getList() {
        return list;
    }

    public void setList(List<Criteria> list) {
        this.list = list;
    }

    public List<Flight> result(List<Flight> flight) {
        List<Flight> flightList = flight;
        for (Criteria criteria : list) {
            flightList.retainAll(criteria.criteriaFlights());
        }
        return flightList;
    }
}
