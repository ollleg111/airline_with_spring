package com.spring_db.service;

import com.spring_db.dao.GeneralDAO;
import com.spring_db.entity.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassengerService extends GeneralService<Passenger>{

    @Autowired
    public PassengerService(GeneralDAO<Passenger> dao) {
        super(dao);
    }
}
