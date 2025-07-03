package com.example.visitor_crm_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VisitorResponseDTO {
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String specialNote;
    private String tripType;
    private Integer numberOfPersons;

    private CompanyDTO company;
    private DriverDTO driver;
    private LocationDTO location;

    private List<TripDTO> trips;

    @Getter
    @Setter
    public static class TripDTO {
        private String flightNumber;
        private String direction;
        private String datetime;

        private LocationDTO fromLocation;
        private LocationDTO toLocation;

        private CompanyDTO company;
        private VehicleDTO vehicle;
        private DriverDTO driver;
    }

    @Getter
    @Setter
    public static class CompanyDTO {
        private Long id;
        private String name;
        // getters and setters
    }

    @Getter
    @Setter
    public static class DriverDTO {
        private Long id;
        private String fullName;
        private String licenseNumber;
        // getters and setters
    }

    @Getter
    @Setter
    public static class LocationDTO {
        private Long id;
        private String location;
        private String description;
    }

    @Getter
    @Setter
    public static class VehicleDTO {
        private Long id;
        private String brand;
        private String model;
        private String type;
        private String plateNumber;
    }

    // Getters & Setters
}


