package com.spring_db.service;

import com.spring_db.dao.FlightDAO;
import com.spring_db.dao.GeneralDAO;
import com.spring_db.entity.Flight;
import com.spring_db.exceptions.BadRequestException;
import com.spring_db.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService extends GeneralService<Flight>{

    private FlightDAO flightDAO;

    @Autowired
    public FlightService(GeneralDAO<Flight> dao, FlightDAO flightDAO) {
        super(dao);
        this.flightDAO = flightDAO;
    }

    @Override
    public Flight findById(Long id) throws ServiceException {
        Flight flight = flightDAO.getOne(id);
        flightNullValidator(flight);
        return super.findById(id);
    }

    @Override
    public void save(Flight flight) throws ServiceException {
        super.save(flight);
    }

    @Override
    public void update(Flight flight) throws ServiceException {
        super.update(flight);
    }

    @Override
    public void delete(Flight flight) throws ServiceException {
        super.delete(flight);
    }

    public void deleteById(Long id) throws ServiceException {
        Flight flight = flightDAO.getOne(id);
        flightNullValidator(flight);
        super.delete(flight);
    }

    @Override
    public List<Flight> findAll() throws ServiceException {
        return super.findAll();
    }

    private void flightNullValidator(Flight flight) throws RuntimeException {
        if (flight == null) throw new BadRequestException("Flight does not exist in method" +
                " flightNullValidator(Flight flight) from class " +
                FlightService.class.getName());
    }
}
