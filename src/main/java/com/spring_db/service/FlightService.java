package com.spring_db.service;

import com.spring_db.dao.GeneralDAO;
import com.spring_db.entity.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightService extends GeneralService<Flight>{

    @Autowired
    public FlightService(GeneralDAO<Flight> dao) {
        super(dao);
    }
}
