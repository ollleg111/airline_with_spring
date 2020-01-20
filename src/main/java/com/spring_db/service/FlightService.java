package com.spring_db.service;

import com.spring_db.dao.FlightDAO;
import com.spring_db.dao.GeneralDAO;
import com.spring_db.entity.Filter;
import com.spring_db.entity.Flight;
import com.spring_db.exceptions.BadRequestException;
import com.spring_db.exceptions.DaoException;
import com.spring_db.exceptions.ServiceException;
import com.spring_db.service.filter.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FlightService extends GeneralService<Flight> {

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

    private String alarmMessage = FlightService.class.getName();

    private FlightDAO flightDAO;
    private Criteria criteria;
    private MainList mainList;

    private FilterCityFromFlightsList filterCityFromFlightsList;
    private FilterCityToFlightsList filterCityToFlightsList;
    private FilterDatesFlightsList filterDatesFlightsList;
    private FilterPlaneModelsList filterPlaneModelsList;
    private FilterTodayDateFlightsList filterTodayDateFlightsList;


    CriteriaBuilder cb;
    CriteriaQuery<Flight> cq;
    TypedQuery<Flight> typedQuery;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public FlightService(GeneralDAO<Flight> dao, FlightDAO flightDAO) {
        super(dao);
        this.flightDAO = flightDAO;
    }

    @Autowired
    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    @Autowired
    public void setMainList(MainList mainList) {
        this.mainList = mainList;
    }

    @Autowired
    public void setFilterCityFromFlightsList(FilterCityFromFlightsList filterCityFromFlightsList) {
        this.filterCityFromFlightsList = filterCityFromFlightsList;
    }

    @Autowired
    public void setFilterCityToFlightsList(FilterCityToFlightsList filterCityToFlightsList) {
        this.filterCityToFlightsList = filterCityToFlightsList;
    }

    @Autowired
    public void setFilterDatesFlightsList(FilterDatesFlightsList filterDatesFlightsList) {
        this.filterDatesFlightsList = filterDatesFlightsList;
    }

    @Autowired
    public void setFilterPlaneModelsList(FilterPlaneModelsList filterPlaneModelsList) {
        this.filterPlaneModelsList = filterPlaneModelsList;
    }

    @Autowired
    public void setFilterTodayDateFlightsList(FilterTodayDateFlightsList filterTodayDateFlightsList) {
        this.filterTodayDateFlightsList = filterTodayDateFlightsList;
    }

    @Override
    public Flight findById(Long id) throws ServiceException {
        Flight flight = flightDAO.getOne(id);
//        flightNullValidator(flight);
        GeneralService.nullValidator(flight);
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

    public void deleteById(Long id) throws ServiceException {
        Flight flight = flightDAO.getOne(id);
//        flightNullValidator(flight);
        GeneralService.nullValidator(flight);
        super.delete(flight);
    }

    @Override
    public List<Flight> findAll() throws ServiceException {
        return super.findAll();
    }

    /*
    список рейсов по дате (в один день),
    по промежутку даты (с даты-по дату),
    городу отправки,
    городу назначения,
    модели самолета
     */
    public List<Flight> flightsByDate(Filter filter) throws ServiceException {
        validate(filter);
        try {
            criteria.setFilter(filter);
            mainList
                    .setList(Arrays.asList(
                            filterTodayDateFlightsList,
                            filterCityFromFlightsList,
                            filterCityToFlightsList,
                            filterDatesFlightsList,
                            filterPlaneModelsList));
            return mainList
                    .result(Stream.of(
                            filterTodayDateFlightsList.criteriaFlights(),
                            filterCityFromFlightsList.criteriaFlights(),
                            filterCityToFlightsList.criteriaFlights(),
                            filterDatesFlightsList.criteriaFlights(),
                            filterPlaneModelsList.criteriaFlights())
                            .flatMap(Collection::stream)
                            .collect(Collectors.toList()));

        } catch (DaoException exception) {
            System.err.println(exception.getMessage());
            throw new ServiceException("Operation was filed in method" +
                    " flightsByDate(Filter filter) from class " +
                    alarmMessage);
        }
    }

    // SELECT * FROM FLIGHT WHERE CITY_FROM = ?
    @Transactional
    public List<Flight> cityFromFlightsList(String cityFrom) throws ServiceException {
        try {
            cb = entityManager.getCriteriaBuilder();
            cq = cb.createQuery(Flight.class);
            Root<Flight> flightRoot = cq.from(Flight.class);
            cq.select(flightRoot).where(cb.equal(flightRoot.get("CITY_FROM"), cityFrom));
            typedQuery = entityManager.createQuery(cq);

            return typedQuery.getResultList();
        } catch (DaoException exception) {
            System.err.println(exception.getMessage());
            throw new ServiceException("Operation was filed in method" +
                    " cityFromFlightsList(String cityFrom) from class " + alarmMessage);
        }
    }

    // SELECT * FROM FLIGHT WHERE CITY_TO = ?
    @Transactional
    public List<Flight> cityToFlightsList(String cityTo) throws ServiceException {
        try {
            cb = entityManager.getCriteriaBuilder();
            cq = cb.createQuery(Flight.class);
            Root<Flight> flightRoot = cq.from(Flight.class);
            cq.select(flightRoot).where(cb.equal(flightRoot.get("CITY_TO"), cityTo));
            typedQuery = entityManager.createQuery(cq);

            return typedQuery.getResultList();
        } catch (DaoException exception) {
            System.err.println(exception.getMessage());
            throw new ServiceException("Operation was filed in method" +
                    " cityToFlightsList(String cityTo) from class " + alarmMessage);
        }
    }

    // SELECT * FROM FLIGHT WHERE DATE_FLIGHT <= ? AND DATE_FLIGHT >= ?
    @Transactional
    public List<Flight> datesFlightsList(Date dateFrom, Date dateTo) throws ServiceException {
        try {
            cb = entityManager.getCriteriaBuilder();
            cq = cb.createQuery(Flight.class);
            Root<Flight> flightRoot = cq.from(Flight.class);
            cq.select(flightRoot).where(cb.between(flightRoot.get("DATE_FLIGHT"), dateFrom, dateTo));
            typedQuery = entityManager.createQuery(cq);

            return typedQuery.getResultList();
        } catch (DaoException exception) {
            System.err.println(exception.getMessage());
            throw new ServiceException("Operation was filed in method" +
                    " datesFlightsList(Date dateFrom, Date dateTo) from class " + alarmMessage);
        }
    }

    // SELECT * FROM FLIGHT WHERE PLANE = ?
    @Transactional
    public List<Flight> planeModelsList(String planeModel) throws ServiceException {
        try {
            cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Flight> cq = cb.createQuery(Flight.class);
            Root<Flight> flightRoot = cq.from(Flight.class);
            cq.select(flightRoot).where(cb.equal(flightRoot.get("PLANE"), planeModel));
            typedQuery = entityManager.createQuery(cq);

            return typedQuery.getResultList();
        } catch (DaoException exception) {
            System.err.println(exception.getMessage());
            throw new ServiceException("Operation was filed in method" +
                    " planeModelsList(String planeModel) from class " + alarmMessage);
        }
    }

    // SELECT * FROM FLIGHT WHERE DATE_FLIGHT = ?
    @Transactional
    public List<Flight> todayDateFlightList(Date today) throws ServiceException {
        try {
            cb = entityManager.getCriteriaBuilder();
            cq = cb.createQuery(Flight.class);
            Root<Flight> flightRoot = cq.from(Flight.class);
            cq.select(flightRoot).where(cb.equal(flightRoot.get("DATE_FLIGHT"), today));
            typedQuery = entityManager.createQuery(cq);

            return typedQuery.getResultList();
        } catch (DaoException exception) {
            System.err.println(exception.getMessage());
            throw new ServiceException("Operation was filed in method" +
                    " todayDateFlightList(Date today) from class " + alarmMessage);
        }
    }

    /*
    mostPopularTo() - список ТОП 10 самых популярных рейсов по городам назначения
     */
    public Map<String, List<Flight>> mostPopularTo() throws ServiceException {
        return mostPopular(
                MOST_POPULAR_CITY_TO_REQUEST,
                MOST_POPULAR_FLIGHTS_TO_CITY_REQUEST);
    }

    /*
    mostPopularFrom() - список ТОП 10 самых популярных рейсов по городам вылета
     */
    public Map<String, List<Flight>> mostPopularFrom() throws ServiceException {
        return mostPopular(
                MOST_POPULAR_CITY_FROM_REQUEST,
                MOST_POPULAR_FLIGHTS_FROM_CITY_REQUEST);
    }

    @Transactional
    public Map<String, List<Flight>> mostPopular(
            String cityRequestString,
            String flightRequestString) throws ServiceException {
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
            throw new ServiceException("Operation was filed in method" +
                    " mostPopular() from class " + alarmMessage);
        }
    }

    private void validate(Filter filter) throws BadRequestException {
        if (filter.getOneDayFlight() == null) {
            throw new BadRequestException("Wrong Date's field in oneDayFlight from method validate(Filter filter) " +
                    "in class " + alarmMessage);
        }
        if (filter.getDateFrom() == null) {
            throw new BadRequestException("Wrong Date's field in dateFrom from method validate(Filter filter) " +
                    "in class " + alarmMessage);
        }
        if (filter.getDateTo() == null) {
            throw new BadRequestException("Wrong Date's field in dateTo from method validate(Filter filter) " +
                    "in class " + alarmMessage);
        }
        if (filter.getCityFrom() == null) {
            throw new BadRequestException("Wrong City's field in cityFrom from method validate(Filter filter) " +
                    "in class " + alarmMessage);
        }
        if (filter.getCityTo() == null) {
            throw new BadRequestException("Wrong City's field in cityTo from method validate(Filter filter) " +
                    "in class " + alarmMessage);
        }
        if (filter.getPlaneModel() == null) {
            throw new BadRequestException("Wrong Plane's field in planeModel from method validate(Filter filter) " +
                    "in class " + alarmMessage);
        }
    }

    /*
    private void flightNullValidator(Flight flight) throws RuntimeException {
        if (flight == null) throw new BadRequestException("Flight does not exist in method" +
                " flightNullValidator(Flight flight) from class " +
                FlightService.class.getName());
    }
     */
}
