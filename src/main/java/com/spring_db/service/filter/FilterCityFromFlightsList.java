package com.spring_db.service.filter;

import com.spring_db.entity.Filter;
import com.spring_db.entity.Flight;
import com.spring_db.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FilterCityFromFlightsList extends Criteria {
    private FlightService flightService;

    @Autowired
    public FilterCityFromFlightsList(FlightService flightService) {
        this.flightService = flightService;
    }

    @Override
    public Filter getFilter() {
        return super.getFilter();
    }

    @Override
    public void setFilter(Filter filter) {
        super.setFilter(filter);
    }

    @Override
    public List<Flight> criteriaFlights() {
        return flightService.cityFromFlightsList(getFilter().getCityFrom());
    }
}
