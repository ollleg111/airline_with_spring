package com.spring_db.controller;

import com.spring_db.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;

public class PassengerController {

    private PassengerService passengerService;

    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }
}
