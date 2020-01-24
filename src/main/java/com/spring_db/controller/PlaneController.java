package com.spring_db.controller;

import com.spring_db.entity.Plane;
import com.spring_db.exceptions.BadRequestException;
import com.spring_db.exceptions.DaoException;
import com.spring_db.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/plane")
public class PlaneController {

    private PlaneService planeService;

    @Autowired
    public PlaneController(PlaneService planeService) {
        this.planeService = planeService;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/find",
            produces = "text/plain")
    public ResponseEntity<Plane> findById(@RequestParam(value = "id") Long id) throws DaoException {
        try {
            return new ResponseEntity<>(planeService.findById(id), HttpStatus.OK);
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
    public ResponseEntity<Plane> save(@RequestBody Plane plane) throws DaoException {
        try {
            return new ResponseEntity<>(planeService.save(plane), HttpStatus.CREATED);
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
    public ResponseEntity<Plane> update(@RequestBody Plane plane) throws DaoException {
        try {
            return new ResponseEntity<>(planeService.update(plane), HttpStatus.OK);
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
    public ResponseEntity<String> delete(@RequestBody Plane plane) throws DaoException {
        try {
            planeService.delete(plane);
            return new ResponseEntity<>(" Plane was deleted ", HttpStatus.OK);
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
    public ResponseEntity<String> deleteById(@RequestParam(value = "id") Long id) throws DaoException {
        try {
            planeService.deleteById(id);
            return new ResponseEntity<>(" Plane was deleted ", HttpStatus.OK);
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
    public ResponseEntity<List<Plane>> getAll() throws DaoException {
        try {
            return new ResponseEntity<>(planeService.findAll(), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
    oldPlanes() - самолеты старше 20 лет
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/oldPlanes",
            produces = "text/plain")
    public ResponseEntity<List<Plane>> oldPlanes() throws DaoException {
        try {
            return new ResponseEntity<>(planeService.oldPlanes(), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
    regularPlanes(int year) - самолеты, которые с больше 300 полетов за год
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/regularPlanes",
            produces = "text/plain")
    public ResponseEntity<List<Plane>> regularPlanes(@RequestParam(value = "year") Integer year)
            throws DaoException {
        try {
            return new ResponseEntity<>(planeService.regularPlanes(year), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}