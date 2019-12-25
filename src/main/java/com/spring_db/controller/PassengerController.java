package com.spring_db.controller;

import com.spring_db.entity.Passenger;
import com.spring_db.exceptions.ServiceException;
import com.spring_db.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PassengerController {

    private Passenger passenger;
    private PassengerService passengerService;

    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @Autowired
    public PassengerController(Passenger passenger) {
        this.passenger = passenger;
    }

    public Passenger findById(Long id) throws ServiceException {
        return passengerService.findById(id);
    }

    public void add(Passenger passenger) throws ServiceException {
        passengerService.add(passenger);
    }

    public void update(Passenger passenger) throws ServiceException {
        passengerService.update(passenger);
    }

    public void remove(Passenger passenger) throws ServiceException {
        passengerService.remove(passenger);
    }

    public List<Passenger> getAll() throws ServiceException {
        return passengerService.getAll();
    }
}
