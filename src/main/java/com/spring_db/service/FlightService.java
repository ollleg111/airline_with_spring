package com.spring_db.service;

import com.spring_db.dao.FlightDAO;
import com.spring_db.dao.GeneralDAO;
import com.spring_db.entity.Flight;
import com.spring_db.exceptions.BadRequestException;
import com.spring_db.exceptions.DaoException;
import com.spring_db.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FlightService extends GeneralService<Flight> {

    private static final String MOST_POPULAR_CITY_TO_REQUEST = "";
    private static final String MOST_POPULAR_CITY_FROM_REQUEST = "";
    private static final String MOST_POPULAR_FLIGHTS_CITY_TO_REQUEST = "";
    private static final String MOST_POPULAR_FLIGHTS_CITY_FROM_REQUEST = "";

    private FlightDAO flightDAO;

    @PersistenceContext
    EntityManager entityManager;

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
    public Flight save(Flight flight) throws ServiceException {
        return super.save(flight);
    }

    @Override
    public Flight update(Flight flight) throws ServiceException {
        return super.update(flight);
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

    /*
    mostPopularTo() - список ТОП 10 самых популярных рейсов по городам назначения
     */
    public Map<String, List<Flight>> mostPopularTo() throws ServiceException {
        return mostPopular(
                MOST_POPULAR_CITY_TO_REQUEST,
                MOST_POPULAR_FLIGHTS_CITY_TO_REQUEST);
    }

    /*
    mostPopularFrom() - список ТОП 10 самых популярных рейсов по городам вылета
     */
    public Map<String, List<Flight>> mostPopularFrom() throws ServiceException {
        return mostPopular(
                MOST_POPULAR_CITY_FROM_REQUEST,
                MOST_POPULAR_FLIGHTS_CITY_FROM_REQUEST);
    }

    @Transactional
    public Map<String, List<Flight>> mostPopular(
            String cityRequestString,
            String flightRequestString) throws ServiceException {
        Map<String, List<Flight>> map;
        try {
            Query query = entityManager.createNativeQuery(cityRequestString, String.class);
            map = new HashMap<>();
            List<String> mostPopularCity = query.getResultList();

            for (String city : mostPopularCity) {
                Query queryFlights = entityManager.createNativeQuery(flightRequestString, Flight.class);
                queryFlights.setParameter("CITY", city);
                map.put(city, queryFlights.getResultList());
            }
            return map;
        } catch (DaoException exception) {
            System.err.println(exception.getMessage());
            throw new ServiceException("Operation was filed in method" +
                    " mostPopular() from class " + FlightService.class.getName());
        }
    }
}
