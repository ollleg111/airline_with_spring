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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FlightService extends GeneralService<Flight> {

    private static final String MOST_POPULAR_CITY_TO_REQUEST = "";
    private static final String MOST_POPULAR_CITY_FROM_REQUEST = "";
    private static final String MOST_POPULAR_FLIGHTS_TO_CITY_REQUEST = "";
    private static final String MOST_POPULAR_FLIGHTS_FROM_CITY_REQUEST = "";

    private FlightDAO flightDAO;
    private Criteria criteria;
    private MainList mainList;

    private CityFromFlightsList cityFromFlightsList;
    private CityToFlightsList cityToFlightsList;
    private DatesFlightsList datesFlightsList;
    private PlaneModelsList planeModelsList;
    private TodayDateFlightsList todayDateFlightsList;

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
    public void setCityFromFlightsList(CityFromFlightsList cityFromFlightsList) {
        this.cityFromFlightsList = cityFromFlightsList;
    }

    @Autowired
    public void setCityToFlightsList(CityToFlightsList cityToFlightsList) {
        this.cityToFlightsList = cityToFlightsList;
    }

    @Autowired
    public void setDatesFlightsList(DatesFlightsList datesFlightsList) {
        this.datesFlightsList = datesFlightsList;
    }

    @Autowired
    public void setPlaneModelsList(PlaneModelsList planeModelsList) {
        this.planeModelsList = planeModelsList;
    }

    @Autowired
    public void setTodayDateFlightsList(TodayDateFlightsList todayDateFlightsList) {
        this.todayDateFlightsList = todayDateFlightsList;
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
                            todayDateFlightsList,
                            cityFromFlightsList,
                            cityToFlightsList,
                            datesFlightsList,
                            planeModelsList));
            return mainList
                    .result(Stream.of(
                            todayDateFlightsList.criteriaFlights(),
                            cityFromFlightsList.criteriaFlights(),
                            cityToFlightsList.criteriaFlights(),
                            datesFlightsList.criteriaFlights(),
                            planeModelsList.criteriaFlights())
                            .flatMap(Collection::stream)
                            .collect(Collectors.toList()));

        } catch (DaoException exception) {
            System.err.println(exception.getMessage());
            throw new ServiceException("Operation was filed in method" +
                    " flightsByDate(Filter filter) from class " +
                    FlightService.class.getName());
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

    private void validate(Filter filter) throws BadRequestException {
        if (filter.getOneDayFlight() == null) {
            throw new BadRequestException("Wrong Date's field in oneDayFlight from method validate(Filter filter) " +
                    "in class " + FlightService.class.getName());
        }
        if (filter.getDateFrom() == null) {
            throw new BadRequestException("Wrong Date's field in dateFrom from method validate(Filter filter) " +
                    "in class " + FlightService.class.getName());
        }
        if (filter.getDateTo() == null) {
            throw new BadRequestException("Wrong Date's field in dateTo from method validate(Filter filter) " +
                    "in class " + FlightService.class.getName());
        }
        if (filter.getCityFrom() == null) {
            throw new BadRequestException("Wrong City's field in cityFrom from method validate(Filter filter) " +
                    "in class " + FlightService.class.getName());
        }
        if (filter.getCityTo() == null) {
            throw new BadRequestException("Wrong City's field in cityTo from method validate(Filter filter) " +
                    "in class " + FlightService.class.getName());
        }
        if (filter.getPlaneModel() == null) {
            throw new BadRequestException("Wrong Plane's field in planeModel from method validate(Filter filter) " +
                    "in class " + FlightService.class.getName());
        }
    }

//    private void flightNullValidator(Flight flight) throws RuntimeException {
//        if (flight == null) throw new BadRequestException("Flight does not exist in method" +
//                " flightNullValidator(Flight flight) from class " +
//                FlightService.class.getName());
//    }
}
