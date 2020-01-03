package com.spring_db.controller;

import com.spring_db.entity.Filter;
import com.spring_db.entity.Flight;
import com.spring_db.exceptions.BadRequestException;
import com.spring_db.exceptions.ServiceException;
import com.spring_db.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/flight")
public class FlightController {

    private FlightService flightService;
    private Filter filter;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @Autowired
    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/find",
            produces = "text/plain")
    public ResponseEntity<Flight> findById(@RequestParam(value = "id") Long id) throws ServiceException {
        try {
            return new ResponseEntity<>(flightService.findById(id), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/save",
            produces = "text/plain")
    public ResponseEntity<Flight> save(@RequestBody Flight flight) throws ServiceException {
        try {
            return new ResponseEntity<>(flightService.save(flight), HttpStatus.CREATED);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/update",
            produces = "text/plain")
    public ResponseEntity<Flight> update(@RequestBody Flight flight) throws ServiceException {
        try {
            return new ResponseEntity<>(flightService.update(flight), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/delete",
            produces = "text/plain")
    public ResponseEntity<String> delete(@RequestBody Flight flight) throws ServiceException {
        try {
            flightService.delete(flight);
            return new ResponseEntity<>(" Flight was deleted ", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/deleteById",
            produces = "text/plain")
    public ResponseEntity<String> deleteById(@RequestParam(value = "id") Long id) throws ServiceException {
        try {
            flightService.deleteById(id);
            return new ResponseEntity<>(" Flight was deleted ", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/findAll",
            produces = "text/plain")
    public ResponseEntity<List<Flight>> getAll() throws ServiceException {
        try {
            return new ResponseEntity<>(flightService.findAll(), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
    список рейсов по дате (в один день),
    по промежутку даты (с даты-по дату),
    городу отправки,
    городу назначения,
    модели самолета
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/flightsByDate",
            produces = "text/plain")
    public ResponseEntity<List<Long>> flightsByDate(
            //TODO
            @RequestParam(value = "oneDayFlight") String inputOneDayFlight,
            @RequestParam(value = "dateFrom") String inputDateFrom,
            @RequestParam(value = "dateTo") String inputDateTo,
            @RequestParam(value = "cityFrom") String inputCityFrom,
            @RequestParam(value = "cityTo") String inputCityTo,
            @RequestParam(value = "planeModel") String inputPlaneModel
    ) throws ServiceException {
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

            if (!inputOneDayFlight.isEmpty())
                filter.setOneDayFlight(dateFormat.parse(inputOneDayFlight));
            if (!inputDateFrom.isEmpty())
                filter.setDateFrom(dateFormat.parse(inputDateFrom));
            if (!inputDateTo.isEmpty())
                filter.setDateTo(dateFormat.parse(inputDateTo));

            if (!inputCityFrom.isEmpty())
                filter.setCityFrom(inputCityFrom);
            if (!inputCityTo.isEmpty())
                filter.setCityTo(inputCityTo);
            if (!inputPlaneModel.isEmpty())
                filter.setPlaneModel(inputPlaneModel);

            return new ResponseEntity<>(
                    flightService.flightsByDate(filter),
                    HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
    mostPopularTo() - список ТОП 10 самых популярных рейсов по городам назначения
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/mostPopularTo",
            produces = "text/plain")
    public ResponseEntity<String> mostPopularTo() throws ServiceException {
        try {
            return new ResponseEntity<>(
                    resultString(flightService.mostPopularTo()),
                    HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
    mostPopularFrom() - список ТОП 10 самых популярных рейсов по городам вылета
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/mostPopularFrom",
            produces = "text/plain")
    public ResponseEntity<String> mostPopularFrom() throws ServiceException {
        try {
            return new ResponseEntity<>(
                    resultString(flightService.mostPopularFrom()),
                    HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String resultString(Map<String, List<Flight>> map) {
        Set<String> cities = map.keySet();
        StringBuilder stringBuilder = new StringBuilder();
        int count = 1;
        for (String city : cities) {
            stringBuilder.append(count).append(". ").append(city).append(": ").append(map.get(city));
            count++;
        }
        return stringBuilder.toString();
    }
}
