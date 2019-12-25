package com.spring_db.controller;

import com.spring_db.entity.Flight;
import com.spring_db.exceptions.ServiceException;
import com.spring_db.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FlightController {

    private Flight flight;
    private FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    public FlightController(Flight flight) {
        this.flight = flight;
    }

    public Flight findById(Long id) throws ServiceException {
        return flightService.findById(id);
    }

    public void add(Flight flight) throws ServiceException {
        flightService.add(flight);
    }

    public void update(Flight flight) throws ServiceException {
        flightService.update(flight);
    }

    public void remove(Flight flight) throws ServiceException {
        flightService.remove(flight);
    }

    public List<Flight> getAll() throws ServiceException {
        return flightService.getAll();
    }
}
