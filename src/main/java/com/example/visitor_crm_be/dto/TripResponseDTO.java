package com.example.visitor_crm_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TripResponseDTO {
    private String flightNumber;
    private String direction;
    private String datetime;

    private VisitorResponseDTO.LocationDTO fromLocation;
    private VisitorResponseDTO.LocationDTO toLocation;

    private VisitorResponseDTO.CompanyDTO company;
    private VisitorResponseDTO.VehicleDTO vehicle;
    private VisitorResponseDTO.DriverDTO driver;

    private String httpMessage;
}