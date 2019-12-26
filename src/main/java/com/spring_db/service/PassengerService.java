package com.spring_db.service;

import com.spring_db.dao.GeneralDAO;
import com.spring_db.dao.PassengerDAO;
import com.spring_db.entity.Passenger;
import com.spring_db.exceptions.BadRequestException;
import com.spring_db.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService extends GeneralService<Passenger>{

    private PassengerDAO passengerDAO;

    @Autowired
    public PassengerService(GeneralDAO<Passenger> dao, PassengerDAO passengerDAO) {
        super(dao);
        this.passengerDAO = passengerDAO;
    }

    @Override
    public Passenger findById(Long id) throws ServiceException {
        Passenger passenger = passengerDAO.getOne(id);
        passengerNullValidator(passenger);
        return super.findById(id);
    }

    @Override
    public void save(Passenger passenger) throws ServiceException {
        super.save(passenger);
    }

    @Override
    public void update(Passenger passenger) throws ServiceException {
        super.update(passenger);
    }

    @Override
    public void delete(Passenger passenger) throws ServiceException {
        super.delete(passenger);
    }

    public void deleteById(Long id) throws ServiceException {
        Passenger passenger = passengerDAO.getOne(id);
        passengerNullValidator(passenger);
        super.delete(passenger);
    }

    @Override
    public List<Passenger> findAll() throws ServiceException {
        return super.findAll();
    }

    private void passengerNullValidator(Passenger passenger) throws RuntimeException {
        if (passenger == null) throw new BadRequestException("Passenger does not exist in method" +
                " passengerNullValidator(Passenger passenger) from class " +
                PassengerService.class.getName());
    }
}
