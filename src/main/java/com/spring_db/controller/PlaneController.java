package com.spring_db.controller;

import com.spring_db.entity.Plane;
import com.spring_db.exceptions.BadRequestException;
import com.spring_db.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    public ResponseEntity<String> findById(@RequestParam(value = "id") Long id) {
        try {
            planeService.findById(id);
            return new ResponseEntity<>(" Plane was found ", HttpStatus.OK);
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
    public ResponseEntity<String> save(@RequestBody Plane plane) {
        try {
            planeService.save(plane);
            return new ResponseEntity<>(" Plane was saved ", HttpStatus.CREATED);
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
    public ResponseEntity<String> update(@RequestBody Plane plane) {
        try {
            planeService.update(plane);
            return new ResponseEntity<>(" Plane was updated ", HttpStatus.OK);
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
    public ResponseEntity<String> delete(@RequestBody Plane plane) {
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
    public ResponseEntity<String> deleteById(@RequestParam(value = "id") Long id) {
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
    public ResponseEntity<String> getAll() {
        try {
            planeService.findAll();
            return new ResponseEntity<>(" List<Plane> was found ", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/oldPlanes",
            produces = "text/plain")
    public ResponseEntity<String> oldPlanes() {
        try {
            planeService.oldPlanes();
            //TODO
            return new ResponseEntity<>(" Quantity of planes was found ", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/regularPlanes",
            produces = "text/plain")
    public ResponseEntity<String> regularPlanes() {
        try {
            planeService.regularPlanes();
            //TODO
            return new ResponseEntity<>(" List<Planes> was found ", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}