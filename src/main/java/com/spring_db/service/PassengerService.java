package com.spring_db.service;

import com.spring_db.dao.GeneralDAO;
import com.spring_db.entity.Passenger;
import com.spring_db.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService extends GeneralService<Passenger>{

    @Autowired
    public PassengerService(GeneralDAO<Passenger> dao) {
        super(dao);
    }

    @Override
    public Passenger findById(Long id) throws ServiceException {
        return super.findById(id);
    }

    @Override
    public void add(Passenger passenger) throws ServiceException {
        super.add(passenger);
    }

    @Override
    public void update(Passenger passenger) throws ServiceException {
        super.update(passenger);
    }

    @Override
    public void remove(Passenger passenger) throws ServiceException {
        super.remove(passenger);
    }

    @Override
    public List<Passenger> getAll() throws ServiceException {
        return super.getAll();
    }
}
