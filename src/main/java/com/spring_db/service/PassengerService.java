package com.spring_db.service;

import com.spring_db.dao.PassengerDAO;
import com.spring_db.entity.Passenger;
import com.spring_db.exceptions.BadRequestException;
import com.spring_db.exceptions.DaoException;
import com.spring_db.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService {

    private PassengerDAO passengerDAO;

    @Autowired
    public PassengerService(PassengerDAO passengerDAO) {
        this.passengerDAO = passengerDAO;
    }

    public Passenger findById(Long id) throws DaoException {
        Passenger passenger = passengerDAO.findById(id);
        passengerNullValidator(passenger);
        return passengerDAO.findById(id);
    }

    public Passenger save(Passenger passenger) throws DaoException {
        return passengerDAO.save(passenger);
    }

    public Passenger update(Passenger passenger) throws DaoException {
        return passengerDAO.update(passenger);
    }

    public void delete(Passenger passenger) throws DaoException {
        passengerDAO.delete(passenger);
    }

    public void deleteById(Long id) throws DaoException {
        Passenger passenger = passengerDAO.findById(id);
        passengerNullValidator(passenger);
        passengerDAO.delete(passenger);
    }

    public List<Passenger> findAll() throws DaoException {
        return passengerDAO.findAll();
    }

    public List<Passenger> regularPassengers(Integer year) throws DaoException {
        return passengerDAO.regularPassengers(year);
    }

    private void passengerNullValidator(Passenger passenger) throws ServiceException {
        if (passenger == null) throw new BadRequestException("Passenger does not exist in method" +
                " passengerNullValidator(Passenger passenger) from class " +
                PassengerService.class.getName());
    }
}
