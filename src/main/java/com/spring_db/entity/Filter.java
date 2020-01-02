package com.spring_db.entity;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Filter {

    /*
                flightsByDate(Filter filter) -
                список рейсов по дате (в один день),
                по промежутку даты (с даты-по дату),
                городу отправки,
                городу назначения,
                модели самолета
     */

    private Date oneDayFlight;
    private Date dateFrom;
    private Date dateTo;
    private String cityFrom;
    private String cityTo;
    private String planeModel;

    public Filter(Date oneDayFlight, Date dateFrom, Date dateTo, String cityFrom,
                  String cityTo, String planeModel) {
        this.oneDayFlight = oneDayFlight;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.cityFrom = cityFrom;
        this.cityTo = cityTo;
        this.planeModel = planeModel;
    }

    public Date getOneDayFlight() {
        return oneDayFlight;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public String getCityFrom() {
        return cityFrom;
    }

    public String getCityTo() {
        return cityTo;
    }

    public String getPlaneModel() {
        return planeModel;
    }

    public void setOneDayFlight(Date oneDayFlight) {
        this.oneDayFlight = oneDayFlight;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public void setCityFrom(String cityFrom) {
        this.cityFrom = cityFrom;
    }

    public void setCityTo(String cityTo) {
        this.cityTo = cityTo;
    }

    public void setPlaneModel(String planeModel) {
        this.planeModel = planeModel;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "oneDAyFlight=" + oneDayFlight +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", cityFrom='" + cityFrom + '\'' +
                ", cityTo='" + cityTo + '\'' +
                ", planeModel='" + planeModel + '\'' +
                '}';
    }
}
