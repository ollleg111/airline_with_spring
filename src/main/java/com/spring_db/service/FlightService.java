package com.spring_db.service;

import com.spring_db.dao.GeneralDAO;
import com.spring_db.entity.Flight;
import com.spring_db.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService extends GeneralService<Flight>{

    @Autowired
    public FlightService(GeneralDAO<Flight> dao) {
        super(dao);
    }

    @Override
    public Flight findById(Long id) throws ServiceException {
        return super.findById(id);
    }

    @Override
    public void add(Flight flight) throws ServiceException {
        super.add(flight);
    }

    @Override
    public void update(Flight flight) throws ServiceException {
        super.update(flight);
    }

    @Override
    public void remove(Flight flight) throws ServiceException {
        super.remove(flight);
    }

    @Override
    public List<Flight> getAll() throws ServiceException {
        return super.getAll();
    }
}
