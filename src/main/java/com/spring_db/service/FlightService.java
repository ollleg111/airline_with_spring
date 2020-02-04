package com.spring_db.service;

import com.spring_db.dao.FlightDAO;
import com.spring_db.entity.Filter;
import com.spring_db.entity.Flight;
import com.spring_db.exceptions.BadRequestException;
import com.spring_db.exceptions.DaoException;
import com.spring_db.exceptions.ServiceException;
import com.spring_db.service.filter.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FlightService {

    private FlightDAO flightDAO;
    private Criteria criteria;
    private MainList mainList;

    private FilterCityFromFlightsList filterCityFromFlightsList;
    private FilterCityToFlightsList filterCityToFlightsList;
    private FilterDatesFlightsList filterDatesFlightsList;
    private FilterPlaneModelsList filterPlaneModelsList;
    private FilterTodayDateFlightsList filterTodayDateFlightsList;

    private String className = FlightService.class.getName();

    @Autowired
    public void setFlightDAO(FlightDAO flightDAO) {
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

    public Flight findById(Long id) throws DaoException {
        Flight flight = flightDAO.findById(id);
        flightNullValidator(flight);
        return flightDAO.findById(id);
    }

    public Flight save(Flight flight) throws DaoException {
        return flightDAO.save(flight);
    }

    public Flight update(Flight flight) throws DaoException {
        return flightDAO.update(flight);
    }

    public void delete(Flight flight) throws DaoException {
        flightDAO.delete(flight);
    }

    public void deleteById(Long id) throws DaoException {
        Flight flight = flightDAO.findById(id);
        flightNullValidator(flight);
        flightDAO.delete(flight);
    }

    public List<Flight> findAll() throws DaoException {
        return flightDAO.findAll();
    }

    /*
    список рейсов по дате (в один день),
    по промежутку даты (с даты-по дату),
    городу отправки,
    городу назначения,
    модели самолета
     */
    public List<Flight> flightsByDate(Filter filter) throws DaoException {
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
                    className);
        }
    }

    public List<Flight> todayDateFlightList(Date oneDayFlight) throws DaoException {
        return flightDAO.todayDateFlightList(oneDayFlight);
    }

    public List<Flight> cityFromFlightsList(String cityFrom) throws DaoException {
        return flightDAO.cityFromFlightsList(cityFrom);
    }

    public List<Flight> cityToFlightsList(String cityTo) throws DaoException {
        return flightDAO.cityToFlightsList(cityTo);
    }

    public List<Flight> datesFlightsList(Date dateFrom, Date dateTo) throws DaoException {
        return flightDAO.datesFlightsList(dateFrom, dateTo);
    }

    public List<Flight> planeModelsList(String planeModel) throws DaoException {
        return flightDAO.planeModelsList(planeModel);
    }

    public Map<String, List<Flight>> mostPopularTo() throws DaoException {
        return flightDAO.mostPopularTo();
    }

    public Map<String, List<Flight>> mostPopularFrom() throws DaoException {
        return flightDAO.mostPopularFrom();
    }

    private void validate(Filter filter) throws ServiceException {
        if (filter.getOneDayFlight() == null) {
            throw new BadRequestException("Wrong Date's field in oneDayFlight from method validate(Filter filter) " +
                    "in class " + className);
        }
        if (filter.getDateFrom() == null) {
            throw new BadRequestException("Wrong Date's field in dateFrom from method validate(Filter filter) " +
                    "in class " + className);
        }
        if (filter.getDateTo() == null) {
            throw new BadRequestException("Wrong Date's field in dateTo from method validate(Filter filter) " +
                    "in class " + className);
        }
        if (filter.getCityFrom() == null) {
            throw new BadRequestException("Wrong City's field in cityFrom from method validate(Filter filter) " +
                    "in class " + className);
        }
        if (filter.getCityTo() == null) {
            throw new BadRequestException("Wrong City's field in cityTo from method validate(Filter filter) " +
                    "in class " + className);
        }
        if (filter.getPlaneModel() == null) {
            throw new BadRequestException("Wrong Plane's field in planeModel from method validate(Filter filter) " +
                    "in class " + className);
        }
    }

    private void flightNullValidator(Flight flight) throws ServiceException {
        if (flight == null) throw new BadRequestException("Flight does not exist in method" +
                " flightNullValidator(Flight flight) from class " +
                FlightService.class.getName());
    }
}
