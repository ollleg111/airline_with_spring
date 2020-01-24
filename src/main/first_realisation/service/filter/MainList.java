//package com.spring_db.first_realisation.dao.service.filter;
//
//import com.spring_db.entity.Flight;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class MainList {
//
//    private List<Criteria> list;
//
//    public MainList(List<Criteria> list) {
//        this.list = list;
//    }
//
//    public List<Criteria> getList() {
//        return list;
//    }
//
//    public void setList(List<Criteria> list) {
//        this.list = list;
//    }
//
//    public List<Flight> result(List<Flight> flight) {
//        for (Criteria criteria : list) {
//            flight.retainAll(criteria.criteriaFlights());
//        }
//        return flight;
//    }
//}
