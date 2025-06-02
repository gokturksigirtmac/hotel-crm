package com.example.visitor_crm_be.dto;

import java.time.OffsetDateTime;

public class VisitorCreateDTO {
    private Long hotelId;
    private Long companyID;
    private Long driverID;
    private Long vehicleID;
    private String fullName;
    private String phoneNumber;
    private String email;
    private int passangers;
    private String oneWayOrRoundTrip;
    private OffsetDateTime departureDateTime;
    private String visitorFrom;
    private String visitorTo;
    private String flightNumber;
    private String price;
    private String currency;
    private String hotelName;
    private String note;

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Long getCompanyID() {
        return companyID;
    }

    public void setCompanyID(Long companyID) {
        this.companyID = companyID;
    }

    public Long getDriverID() {
        return driverID;
    }

    public void setDriverID(Long driverID) {
        this.driverID = driverID;
    }

    public Long getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(Long vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPassangers() {
        return passangers;
    }

    public void setPassangers(int passangers) {
        this.passangers = passangers;
    }

    public String getOneWayOrRoundTrip() {
        return oneWayOrRoundTrip;
    }

    public void setOneWayOrRoundTrip(String oneWayOrRoundTrip) {
        this.oneWayOrRoundTrip = oneWayOrRoundTrip;
    }

    public OffsetDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(OffsetDateTime departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public String getVisitorFrom() {
        return visitorFrom;
    }

    public void setVisitorFrom(String visitorFrom) {
        this.visitorFrom = visitorFrom;
    }

    public String getVisitorTo() {
        return visitorTo;
    }

    public void setVisitorTo(String visitorTo) {
        this.visitorTo = visitorTo;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
