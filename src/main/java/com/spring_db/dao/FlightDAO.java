package com.spring_db.dao;

import com.spring_db.entity.Flight;
import com.spring_db.exceptions.DaoException;
import com.spring_db.service.FlightService;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FlightDAO extends GeneralDAO<Flight> {
    @PersistenceContext
    private EntityManager entityManager;

    public FlightDAO() {
        setEntityManager(entityManager);
        setTypeParameterClass(Flight.class);
    }

    private CriteriaBuilder cb;
    private CriteriaQuery<Flight> cq;
    private TypedQuery<Flight> typedQuery;

    private static final String MOST_POPULAR_CITY_TO_REQUEST =
            "SELECT * FROM (" +
                    "SELECT F.CITY_TO FROM FLIGHT F, FLIGHTS_PASSENGERS FP" +
                    "WHERE F.ID = FP.FLIGHT_ID" +
                    "GROUP BY F.CITY_TO" +
                    "ORDER BY COUNT(FP.FLIGHT_ID) DESC) " +
                    "WHERE ROWNUM <= 10";

    private static final String MOST_POPULAR_CITY_FROM_REQUEST =
            "SELECT * FROM (" +
                    "SELECT F.CITY_TO FROM FLIGHT F, FLIGHTS_PASSENGERS FP" +
                    "WHERE F.ID = FP.FLIGHT_ID" +
                    "GROUP BY F.CITY_FROM" +
                    "ORDER BY COUNT(FP.FLIGHT_ID) DESC) " +
                    "WHERE ROWNUM <= 10";

    private static final String MOST_POPULAR_FLIGHTS_TO_CITY_REQUEST =
            "SELECT * FROM (" +
                    "SELECT F.ID, F.PLANE, F.DATE_FLIGHT, F.CITY_FROM, F.CITY_TO " +
                    "FROM FLIGHT F, FLIGHTS_PASSENGERS FP " +
                    "WHERE F.CITY_TO = ? AND " +
                    "F.ID = FP.FLIGHT_ID " +
                    "GROUP BY F.ID, F.PLANE, F.DATE_FLIGHT, F.CITY_FROM, F.CITY_TO " +
                    "ORDER BY COUNT(FP.FLIGHT_ID) DESC) " +
                    "WHERE ROWNUM <= 10";

    private static final String MOST_POPULAR_FLIGHTS_FROM_CITY_REQUEST =
            "SELECT * FROM (" +
                    "SELECT F.ID, F.PLANE, F.DATE_FLIGHT, F.CITY_FROM, F.CITY_TO " +
                    "FROM FLIGHT F, FLIGHTS_PASSENGERS FP " +
                    "WHERE F.CITY_FROM = ? AND " +
                    "F.ID = FP.FLIGHT_ID " +
                    "GROUP BY F.ID, F.PLANE, F.DATE_FLIGHT, F.CITY_FROM, F.CITY_TO " +
                    "ORDER BY COUNT(FP.FLIGHT_ID) DESC) " +
                    "WHERE ROWNUM <= 10";

    private String className = FlightService.class.getName();

    @Override
    public Flight findById(Long id) throws DaoException {
        return super.findById(id);
    }

    @Override
    public Flight save(Flight flight) throws DaoException {
        return super.save(flight);
    }

    @Override
    public Flight update(Flight flight) throws DaoException {
        return super.update(flight);
    }

    @Override
    public void delete(Flight flight) throws DaoException {
        super.delete(flight);
    }

    @Override
    public List<Flight> findAll() throws DaoException {
        return super.findAll();
    }

    // SELECT * FROM FLIGHT WHERE CITY_FROM = ?
    @Transactional
    public List<Flight> cityFromFlightsList(String cityFrom) throws DaoException {
        try {
            cb = entityManager.getCriteriaBuilder();
            cq = cb.createQuery(Flight.class);
            Root<Flight> flightRoot = cq.from(Flight.class);
            cq.select(flightRoot).where(cb.equal(flightRoot.get("CITY_FROM"), cityFrom));
            typedQuery = entityManager.createQuery(cq);

            return typedQuery.getResultList();
        } catch (DaoException exception) {
            System.err.println(exception.getMessage());
            throw new HibernateException("Operation was filed in method" +
                    " cityFromFlightsList(String cityFrom) from class " + className);
        }
    }

    // SELECT * FROM FLIGHT WHERE CITY_TO = ?
    @Transactional
    public List<Flight> cityToFlightsList(String cityTo) throws DaoException {
        try {
            cb = entityManager.getCriteriaBuilder();
            cq = cb.createQuery(Flight.class);
            Root<Flight> flightRoot = cq.from(Flight.class);
            cq.select(flightRoot).where(cb.equal(flightRoot.get("CITY_TO"), cityTo));
            typedQuery = entityManager.createQuery(cq);

            return typedQuery.getResultList();
        } catch (DaoException exception) {
            System.err.println(exception.getMessage());
            throw new HibernateException("Operation was filed in method" +
                    " cityToFlightsList(String cityTo) from class " + className);
        }
    }

    // SELECT * FROM FLIGHT WHERE DATE_FLIGHT <= ? AND DATE_FLIGHT >= ?
    // SELECT * FROM FLIGHT WHERE DATE_FLIGHT BETWEEN = ? AND = ?
    @Transactional
    public List<Flight> datesFlightsList(Date dateFrom, Date dateTo) throws DaoException {
        try {
            cb = entityManager.getCriteriaBuilder();
            cq = cb.createQuery(Flight.class);
            Root<Flight> flightRoot = cq.from(Flight.class);
            cq.select(flightRoot).where(cb.between(flightRoot.get("DATE_FLIGHT"), dateFrom, dateTo));
            typedQuery = entityManager.createQuery(cq);

            return typedQuery.getResultList();
        } catch (DaoException exception) {
            System.err.println(exception.getMessage());
            throw new HibernateException("Operation was filed in method" +
                    " datesFlightsList(Date dateFrom, Date dateTo) from class " + className);
        }
    }

    // SELECT * FROM FLIGHT WHERE PLANE = ?
    @Transactional
    public List<Flight> planeModelsList(String planeModel) throws DaoException {
        try {
            cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Flight> cq = cb.createQuery(Flight.class);
            Root<Flight> flightRoot = cq.from(Flight.class);
            cq.select(flightRoot).where(cb.equal(flightRoot.get("PLANE"), planeModel));
            typedQuery = entityManager.createQuery(cq);

            return typedQuery.getResultList();
        } catch (DaoException exception) {
            System.err.println(exception.getMessage());
            throw new HibernateException("Operation was filed in method" +
                    " planeModelsList(String planeModel) from class " + className);
        }
    }

    // SELECT * FROM FLIGHT WHERE DATE_FLIGHT = ?
    @Transactional
    public List<Flight> todayDateFlightList(Date today) throws DaoException {
        try {
            cb = entityManager.getCriteriaBuilder();
            cq = cb.createQuery(Flight.class);
            Root<Flight> flightRoot = cq.from(Flight.class);
            cq.select(flightRoot).where(cb.equal(flightRoot.get("DATE_FLIGHT"), today));
            typedQuery = entityManager.createQuery(cq);

            return typedQuery.getResultList();
        } catch (DaoException exception) {
            System.err.println(exception.getMessage());
            throw new HibernateException("Operation was filed in method" +
                    " todayDateFlightList(Date today) from class " + className);
        }
    }

    /*
    mostPopularTo() - список ТОП 10 самых популярных рейсов по городам назначения
     */
    public Map<String, List<Flight>> mostPopularTo() throws DaoException {
        return mostPopular(
                MOST_POPULAR_CITY_TO_REQUEST,
                MOST_POPULAR_FLIGHTS_TO_CITY_REQUEST);
    }

    /*
    mostPopularFrom() - список ТОП 10 самых популярных рейсов по городам вылета
     */
    public Map<String, List<Flight>> mostPopularFrom() throws DaoException {
        return mostPopular(
                MOST_POPULAR_CITY_FROM_REQUEST,
                MOST_POPULAR_FLIGHTS_FROM_CITY_REQUEST);
    }

    @Transactional
    public Map<String, List<Flight>> mostPopular(
            String cityRequestString,
            String flightRequestString) throws DaoException {
        Map<String, List<Flight>> map;
        try {
            Query query = entityManager.createQuery(cityRequestString, String.class);
            map = new HashMap<>();
            List<String> mostPopularCity = query.getResultList();

            for (String city : mostPopularCity) {
                Query queryFlights = entityManager.createNativeQuery(flightRequestString, Flight.class);
                queryFlights.setParameter(1, city);
                map.put(city, queryFlights.getResultList());
            }
            return map;
        } catch (DaoException exception) {
            System.err.println(exception.getMessage());
            throw new HibernateException("Operation was filed in method" +
                    " mostPopular() from class " + className);
        }
    }
}
