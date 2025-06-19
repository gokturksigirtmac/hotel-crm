package com.example.visitor_crm_be.dto;

import com.example.visitor_crm_be.model.Company;
import com.example.visitor_crm_be.model.Driver;
import com.example.visitor_crm_be.model.Hotel;
import com.example.visitor_crm_be.model.Vehicle;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class VisitorCreateDTO {
    private Hotel hotel;
    private Company company;
    private Driver driver;
    private Vehicle vehicle;
    private String fullName;
    private String phoneNumber;
    private String email;
    private int numberofPersons;
    private String oneWayOrRoundTrip;
    private OffsetDateTime departureDateTime;
    private String visitorFrom;
    private String visitorTo;
    private String flightNumber;
    private String price;
    private String currency;
    private String hotelName;
    private String note;

}
