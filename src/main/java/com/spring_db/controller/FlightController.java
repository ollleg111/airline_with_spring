package com.spring_db.controller;

import com.spring_db.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;

public class FlightController {

    private FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }
}
