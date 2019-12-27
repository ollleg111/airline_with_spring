package com.spring_db.service;

import com.spring_db.dao.FlightDAO;
import com.spring_db.dao.GeneralDAO;
import com.spring_db.entity.Flight;
import com.spring_db.entity.Plane;
import com.spring_db.exceptions.BadRequestException;
import com.spring_db.exceptions.DaoException;
import com.spring_db.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service
public class FlightService extends GeneralService<Flight>{

    private FlightDAO flightDAO;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public FlightService(GeneralDAO<Flight> dao, FlightDAO flightDAO) {
        super(dao);
        this.flightDAO = flightDAO;
    }

    private static final String MOST_POPULAR_TO_REQUEST = "";
    private static final String MOST_POPULAR_FROM_REQUEST = "";

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

    @Transactional
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

    @Transactional
    public List mostPopularTo() throws ServiceException {
        try {
            Query query = entityManager.createNativeQuery(MOST_POPULAR_TO_REQUEST, Flight.class);
            return query.getResultList();
        } catch (DaoException exception) {
            System.err.println(exception.getMessage());
            throw new ServiceException("Operation with flights was filed in method" +
                    " mostPopularTo() from class " + FlightService.class.getName());
        }
    }

    @Transactional
    public List mostPopularFrom() throws ServiceException {
        try {
            Query query = entityManager.createNativeQuery(MOST_POPULAR_FROM_REQUEST, Flight.class);
            return query.getResultList();
        } catch (DaoException exception) {
            System.err.println(exception.getMessage());
            throw new ServiceException("Operation with flights was filed in method" +
                    " mostPopularFrom() from class " + FlightService.class.getName());
        }
    }
}
