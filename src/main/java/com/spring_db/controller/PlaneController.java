package com.spring_db.controller;

import com.spring_db.entity.Plane;
import com.spring_db.exceptions.ServiceException;
import com.spring_db.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PlaneController {

    private Plane plane;
    private PlaneService planeService;

    @Autowired
    public PlaneController(Plane plane) {
        this.plane = plane;
    }

    @Autowired
    public PlaneController(PlaneService planeService) {
        this.planeService = planeService;
    }

    public Plane findById(Long id) throws ServiceException {
        return planeService.findById(id);
    }

    public void add(Plane plane) throws ServiceException {
        planeService.add(plane);
    }

    public void update(Plane plane) throws ServiceException {
        planeService.update(plane);
    }

    public void remove(Plane plane) throws ServiceException {
        planeService.remove(plane);
    }

    public List<Plane> getAll() throws ServiceException {
        return planeService.getAll();
    }
}
